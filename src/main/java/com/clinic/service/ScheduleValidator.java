package com.clinic.service;


import com.clinic.model.Clinic;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

@Component
public class ScheduleValidator {

  public boolean isAllowed(Clinic clinic, LocalDate date) {
    // Past dates not allowed
    if (date.isBefore(LocalDate.now())) return false;

    String type = clinic.getRuleType();
    if ("weekdays".equalsIgnoreCase(type)) {
      Set<Integer> weekdays = parseWeekdays(clinic.getWeekdays());
      int dow0to6 = dow0to6(date);
      return weekdays.contains(dow0to6);
    }
    if ("firstSunday".equalsIgnoreCase(type)) {
      return date.equals(nthWeekdayOfMonth(date.getYear(), date.getMonthValue(), 0, 1));
    }
    if ("lastSunday".equalsIgnoreCase(type)) {
      return date.equals(lastWeekdayOfMonth(date.getYear(), date.getMonthValue(), 0));
    }
    if ("lastSaturday".equalsIgnoreCase(type)) {
      return date.equals(lastWeekdayOfMonth(date.getYear(), date.getMonthValue(), 6));
    }
    if ("secondWeekend".equalsIgnoreCase(type)) {
      LocalDate secondSat = nthWeekdayOfMonth(date.getYear(), date.getMonthValue(), 6, 2);
      LocalDate secondSun = secondSat != null ? secondSat.plusDays(1) : null;
      return date.equals(secondSat) || date.equals(secondSun);
    }
    return false;
  }

  private Set<Integer> parseWeekdays(String csv) {
    Set<Integer> set = new TreeSet<>();
    if (csv == null || csv.isBlank()) return set;
    Arrays.stream(csv.split(",")).map(String::trim).filter(s -> !s.isEmpty()).mapToInt(Integer::parseInt).forEach(set::add);
    return set;
  }

  // 0=Sun..6=Sat (align with your front-end)
  private int dow0to6(LocalDate d) {
    DayOfWeek w = d.getDayOfWeek(); // MON..SUN
    int java = w.getValue();        // 1..7
    return java % 7;                // 1..6,0
  }

  private LocalDate nthWeekdayOfMonth(int y, int m1, int weekday0to6, int n) {
    int m = m1 - 1;
    LocalDate first = LocalDate.of(y, m1, 1);
    int firstDow = dow0to6(first);
    int day = 1 + ((7 + weekday0to6 - firstDow) % 7) + (n - 1) * 7;
    LocalDate result = LocalDate.of(y, m1, day);
    return result.getMonthValue() == m1 ? result : null;
  }

  private LocalDate lastWeekdayOfMonth(int y, int m1, int weekday0to6) {
    LocalDate last = LocalDate.of(y, m1, LocalDate.of(y, m1, 1).lengthOfMonth());
    int lastDow = dow0to6(last);
    int diff = (7 + lastDow - weekday0to6) % 7;
    return last.minusDays(diff);
  }
}