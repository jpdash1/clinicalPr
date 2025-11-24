package com.clinic.service;

import com.clinic.model.Doctor;
import com.clinic.model.DoctorOtp;

import com.clinic.notifications.SmsSender;
import com.clinic.repository.DoctorOtpRepository;
import com.clinic.repository.DoctorRepository;
import com.clinic.security.JwtUtil;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class DoctorAuthService {
    private final DoctorRepository doctors;
    private final DoctorOtpRepository otps;
    private final SmsSender smsSender;
    private final Random random = new Random();

    @Value("${otp.rate.limit.max:5}")
    private int maxRequests;
    @Value("${otp.rate.limit.window.seconds:300}")
    private int windowSeconds;

    public DoctorAuthService(DoctorRepository doctors, DoctorOtpRepository otps, SmsSender smsSender) {
        this.doctors = doctors;
        this.otps = otps;
        this.smsSender = smsSender;
    }

    public void requestOtp(String mobileRaw) {
        String mobile = normalize(mobileRaw);

        Doctor doctor = doctors.findByMobile(mobile)
                .filter(Doctor::isActive)
                .orElseThrow(() -> new ValidationException("Doctor not found or inactive."));

        List<DoctorOtp> recent = otps.findTop5ByMobileOrderByIdDesc(mobile);
        long nowMillis = System.currentTimeMillis();
        long windowMillis = windowSeconds * 1000L;

        long recentCount = recent.stream()
                .filter(o -> {
                    Instant created = toInstantSafe(o);
                    return created != null && (nowMillis - created.toEpochMilli()) < windowMillis;
                })
                .count();

        if (recentCount >= maxRequests) {
            throw new ValidationException("Too many OTP requests. Try later.");
        }

        String code = String.format("%06d", random.nextInt(1_000_000));
        DoctorOtp otp = new DoctorOtp();
        otp.setMobile(mobile);
        otp.setCode(code);
        otp.setExpiresAt(new Timestamp(Instant.now().plusSeconds(300).toEpochMilli()));
        // createdAt will be auto-set by @CreationTimestamp

        otps.save(otp);

        try {
            smsSender.sendOtp(mobile, code);
        } catch (Exception e) {
            throw new ValidationException("Failed to send OTP. Please retry.");
        }
    }

    @Transactional
    public String verifyOtp(String mobileRaw, String code) {
        String mobile = normalize(mobileRaw);
        List<DoctorOtp> recent = otps.findTop5ByMobileOrderByIdDesc(mobile);

        Optional<DoctorOtp> match = recent.stream()
                .filter(o -> !o.isConsumed()
                        && o.getCode().equals(code)
                        && o.getExpiresAt().getTime() > System.currentTimeMillis())
                .findFirst();

        if (match.isEmpty()) {
            throw new ValidationException("Invalid or expired OTP.");
        }

        DoctorOtp otp = match.get();
        otp.setConsumed(true);
        otp.setAttempts(otp.getAttempts() + 1);
        otps.save(otp);

        Doctor doctor = doctors.findByMobile(mobile)
                .orElseThrow(() -> new ValidationException("Doctor not found."));

        return JwtUtil.generate(String.valueOf(doctor.getId()), doctor.getMobile(), 3600);
    }

    private String normalize(String raw) {
        if (raw == null) return "";
        String digits = raw.replaceAll("\\D", "");
        if (digits.length() > 10) digits = digits.substring(digits.length() - 10);
        return digits;
    }

    private Instant toInstantSafe(DoctorOtp o) {
        // Handles both Instant and Timestamp possibilities
        try {
            if (o.getCreatedAt() == null) return null;
            // If createdAt field is Instant (as in updated entity):
            if (o.getCreatedAt() instanceof Instant) {
                return (Instant) o.getCreatedAt();
            }
        } catch (Exception ignored) {}
        // For Timestamp version:
        // return o.getCreatedAt().toInstant();
        return null;
    }
}