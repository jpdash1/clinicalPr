package com.clinic.service;


import com.clinic.dto.AppointmentRequest;
import com.clinic.dto.AppointmentResponse;
import com.clinic.model.Appointment;
import com.clinic.model.Clinic;
import com.clinic.repository.AppointmentRepository;
import com.clinic.repository.ClinicRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppointmentService {

    private final ClinicRepository clinics;
    private final AppointmentRepository appointments;
    private final ScheduleValidator scheduleValidator;

    public AppointmentService(ClinicRepository clinics, AppointmentRepository appointments, ScheduleValidator scheduleValidator) {
        this.clinics = clinics;
        this.appointments = appointments;
        this.scheduleValidator = scheduleValidator;
    }

    @Transactional
    public AppointmentResponse create(AppointmentRequest req) {
        Clinic clinic = clinics.findById(req.getClinicId()).orElseThrow(() -> new EntityNotFoundException("Clinic not found"));

        // Validate date fits clinic rule
        if (!scheduleValidator.isAllowed(clinic, req.getDate())) {
            throw new ValidationException("Selected date is not available for this clinic.");
        }

        Appointment appt = new Appointment();
        appt.setClinicId(clinic.getId());
        appt.setClinic(clinic.getClinic());
        appt.setLocation(clinic.getLocation());
        appt.setAppointmentDate(req.getDate());
        appt.setMobile(req.getMobile());
        appt.setFullName(req.getFullName());
        appt.setGender(req.getGender());
        appt.setDob(req.getDob());
        appt.setCity(req.getCity());
        appt.setDistrict(req.getDistrict());
        appt.setState(req.getState());

        Appointment saved = appointments.save(appt);
        return new AppointmentResponse(
                saved.getId(),
                saved.getClinicId(),
                saved.getClinic(),
                saved.getLocation(),
                saved.getAppointmentDate(),
                saved.getFullName(),
                saved.getMobile()
        );
    }
    private String sanitizeMobile(String raw) {
        if (raw == null) return "";
        String digits = raw.replaceAll("\\D", "");
        // Drop country code (like 91) if length is 12 and ends with 10-digit local number
        if (digits.length() > 10) {
            return digits.substring(digits.length() - 10);
        }
        return digits;
    }
}