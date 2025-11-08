package com.clinic.controller;


import com.clinic.dto.AppointmentRequest;
import com.clinic.dto.AppointmentResponse;
import com.clinic.dto.PatientProfileResponse;
import com.clinic.model.Appointment;
import com.clinic.repository.AppointmentRepository;
import com.clinic.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "http://localhost:5173")
public class AppointmentController {

    private final AppointmentService service;
    private final AppointmentRepository appointmentRepository;

    public AppointmentController(AppointmentService service, AppointmentRepository appointmentRepository) {
        this.service = service;
        this.appointmentRepository = appointmentRepository;
    }

    @PostMapping
    public AppointmentResponse create(@Valid @RequestBody AppointmentRequest req) {
        return service.create(req);
    }

    // New: look up the latest patient details by mobile (for prefill)
    // ... inside class
    @GetMapping("/last")
    public PatientProfileResponse findLastByMobile(@RequestParam("mobile") String mobile) {
        String cleaned = mobile == null ? "" : mobile.replaceAll("\\D", "");
        if (cleaned.length() > 10) cleaned = cleaned.substring(cleaned.length() - 10);
        if (!cleaned.matches("\\d{10}")) {
            throw new NoSuchElementException("Invalid mobile or no previous appointment found.");
        }

        Appointment a = appointmentRepository.findTopByMobileOrderByCreatedAtDesc(cleaned)
                .orElseThrow(() -> new NoSuchElementException("No previous appointment found for this mobile."));

        return new PatientProfileResponse(
                a.getMobile(),
                a.getFullName(),
                a.getGender(),
                a.getDob(),
                a.getCity(),
                a.getDistrict(),
                a.getState()
        );
    }

    // Optional: convert NoSuchElement into 404
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public void notFound() { }
}