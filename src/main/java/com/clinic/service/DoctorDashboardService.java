package com.clinic.service;

import com.clinic.dto.DoctorScheduleItem;
import com.clinic.model.Appointment;
import com.clinic.model.Doctor;

import com.clinic.repository.AppointmentRepository;
import com.clinic.repository.DoctorRepository;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DoctorDashboardService {

  private final AppointmentRepository appointments;
  private final DoctorRepository doctors;

  public DoctorDashboardService(AppointmentRepository appointments, DoctorRepository doctors) {
    this.appointments = appointments;
    this.doctors = doctors;
  }

  public String doctorName(Long doctorId) {
    Doctor doctor = doctors.findById(doctorId)
        .orElseThrow(() -> new ValidationException("Doctor not found"));
    return doctor.getFullName();
  }

  public List<DoctorScheduleItem> today(Long doctorId) {
    LocalDate today = LocalDate.now();
    return appointments
        .findByDoctorIdAndAppointmentDateOrderByAppointmentTimeAsc(doctorId, today)
        .stream()
        .map(this::map)
        .toList();
  }

  public List<DoctorScheduleItem> upcoming(Long doctorId) {
    LocalDate start = LocalDate.now().plusDays(1);
    LocalDate end = start.plusDays(7);
    return appointments
        .findUpcomingWindow(doctorId, start, end)
        .stream()
        .map(this::map)
        .toList();
  }

  public long activePatientsWeek(Long doctorId) {
    LocalDate start = LocalDate.now();
    LocalDate end = start.plusDays(7);
    return appointments.countDistinctPatients(doctorId, start, end);
  }

  private DoctorScheduleItem map(Appointment a) {
    return new DoctorScheduleItem(
        a.getId(),
        safe(a.getFullName()),
        safe(a.getMobile()),
        a.getAppointmentDate(),
        a.getAppointmentTime(),
        a.getDurationMinutes() == null ? 30 : a.getDurationMinutes(),
        safe(a.getVisitType()),
        safe(a.getStatus())
    );
  }

  private String safe(String s) { return s == null ? "" : s; }
}