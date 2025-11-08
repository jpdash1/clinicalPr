package com.clinic.repository;

import com.clinic.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment> findTopByMobileOrderByCreatedAtDesc(String mobile);

    List<Appointment> findByDoctorIdAndAppointmentDateOrderByAppointmentTimeAsc(Long doctorId, LocalDate date);

    @Query("""
     SELECT a FROM Appointment a
     WHERE a.doctorId = :doctorId
       AND a.appointmentDate BETWEEN :start AND :end
     ORDER BY a.appointmentDate ASC, a.appointmentTime ASC
     """)
    List<Appointment> findUpcomingWindow(Long doctorId, LocalDate start, LocalDate end);

    @Query("""
     SELECT COUNT(DISTINCT a.mobile) FROM Appointment a
     WHERE a.doctorId = :doctorId
       AND a.appointmentDate BETWEEN :start AND :end
     """)
    long countDistinctPatients(Long doctorId, LocalDate start, LocalDate end);
}