package com.clinic.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class AppointmentRequest {
  @NotBlank
  private String clinicId;

  @NotNull
  private LocalDate date; // appointment date

  @Pattern(regexp = "^[0-9]{10}$")
  private String mobile;

  @NotBlank
  private String fullName;

  @NotBlank
  private String gender; // Male/Female/Other

  @NotNull
  private LocalDate dob;

  @NotBlank private String city;
  @NotBlank private String district;
  @NotBlank private String state;

  public String getClinicId() { return clinicId; }
  public void setClinicId(String clinicId) { this.clinicId = clinicId; }
  public LocalDate getDate() { return date; }
  public void setDate(LocalDate date) { this.date = date; }
  public String getMobile() { return mobile; }
  public void setMobile(String mobile) { this.mobile = mobile; }
  public String getFullName() { return fullName; }
  public void setFullName(String fullName) { this.fullName = fullName; }
  public String getGender() { return gender; }
  public void setGender(String gender) { this.gender = gender; }
  public LocalDate getDob() { return dob; }
  public void setDob(LocalDate dob) { this.dob = dob; }
  public String getCity() { return city; }
  public void setCity(String city) { this.city = city; }
  public String getDistrict() { return district; }
  public void setDistrict(String district) { this.district = district; }
  public String getState() { return state; }
  public void setState(String state) { this.state = state; }
}