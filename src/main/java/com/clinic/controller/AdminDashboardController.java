package com.clinic.controller;

import com.clinic.repository.AppointmentRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/dashboard")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminDashboardController {
  private final AppointmentRepository appointments;

  public AdminDashboardController(AppointmentRepository appointments) {
    this.appointments = appointments;
  }

  @GetMapping("/stats")
  public Map<String,Object> stats() {
    long total = appointments.count();
    long today = appointments.findAll().stream()
        .filter(a -> a.getAppointmentDate() != null && a.getAppointmentDate().equals(LocalDate.now()))
        .count();
    return Map.of("totalAppointments", total, "todayAppointments", today);
  }
}