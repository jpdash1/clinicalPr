package com.clinic.repository;

import com.clinic.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByMobileOrderByCreatedAtDesc(String mobile);
    List<Feedback> findByAppointmentIdOrderByCreatedAtDesc(Long appointmentId);
    List<Feedback> findAllByOrderByCreatedAtDesc();
}
