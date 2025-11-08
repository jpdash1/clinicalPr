package com.clinic.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "clinics")
public class Clinic {
  @Id
  private String id;
  private String clinic;
  private String location;
  private String scheduleLabel;
  private String ruleType;  // weekdays | firstSunday | lastSunday | lastSaturday | secondWeekend
  private String weekdays;  // "1,3,5" for Mon/Wed/Fri; null otherwise

  public String getId() { return id; }
  public void setId(String id) { this.id = id; }

  public String getClinic() { return clinic; }
  public void setClinic(String clinic) { this.clinic = clinic; }

  public String getLocation() { return location; }
  public void setLocation(String location) { this.location = location; }

  public String getScheduleLabel() { return scheduleLabel; }
  public void setScheduleLabel(String scheduleLabel) { this.scheduleLabel = scheduleLabel; }

  public String getRuleType() { return ruleType; }
  public void setRuleType(String ruleType) { this.ruleType = ruleType; }

  public String getWeekdays() { return weekdays; }
  public void setWeekdays(String weekdays) { this.weekdays = weekdays; }
}