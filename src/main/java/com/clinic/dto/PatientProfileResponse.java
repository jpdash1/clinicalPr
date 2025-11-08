package com.clinic.dto;

import java.time.LocalDate;

public class PatientProfileResponse {
  private String mobile;
  private String fullName;
  private String gender;
  private LocalDate dob;
  private String city;
  private String district;
  private String state;

  public PatientProfileResponse() { }

  public PatientProfileResponse(String mobile, String fullName, String gender, LocalDate dob, String city, String district, String state) {
    this.mobile = mobile;
    this.fullName = fullName;
    this.gender = gender;
    this.dob = dob;
    this.city = city;
    this.district = district;
    this.state = state;
  }

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