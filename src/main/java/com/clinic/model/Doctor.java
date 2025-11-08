package com.clinic.model;

import jakarta.persistence.*;

@Entity
@Table(name = "doctors")
public class Doctor {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String fullName;
  @Column(unique = true)
  private String mobile;
  private boolean active = true;
  @Column(insertable = false, updatable = false)
  private java.sql.Timestamp createdAt;

  public Long getId() { return id; }
  public String getFullName() { return fullName; }
  public void setFullName(String fullName) { this.fullName = fullName; }
  public String getMobile() { return mobile; }
  public void setMobile(String mobile) { this.mobile = mobile; }
  public boolean isActive() { return active; }
  public void setActive(boolean active) { this.active = active; }
  public java.sql.Timestamp getCreatedAt() { return createdAt; }
}