package com.clinic.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record DoctorScheduleItem(
    Long id,
    String patientName,
    String patientMobile,
    LocalDate date,
    LocalTime time,
    int durationMinutes,
    String visitType,
    String status
) {}