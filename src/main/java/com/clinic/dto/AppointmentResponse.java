package com.clinic.dto;

import java.time.LocalDate;



import java.time.LocalDate;

public class AppointmentResponse {
  private Long id;
  private String clinicId;
  private String clinic;
  private String location;
  private LocalDate date;
  private String fullName;
  private String mobile;

  public AppointmentResponse(Long id, String clinicId, String clinic, String location, LocalDate date, String fullName, String mobile) {
    this.id = id;
    this.clinicId = clinicId;
    this.clinic = clinic;
    this.location = location;
    this.date = date;
    this.fullName = fullName;
    this.mobile = mobile;
  }

  public Long getId() { return id; }
  public String getClinicId() { return clinicId; }
  public String getClinic() { return clinic; }
  public String getLocation() { return location; }
  public LocalDate getDate() { return date; }
  public String getFullName() { return fullName; }
  public String getMobile() { return mobile; }
}