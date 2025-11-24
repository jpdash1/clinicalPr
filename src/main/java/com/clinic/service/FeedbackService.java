package com.clinic.service;

import com.clinic.dto.FeedbackRequest;
import com.clinic.dto.FeedbackResponse;
import com.clinic.model.Feedback;
import com.clinic.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackService {
    
    private final FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public FeedbackResponse create(FeedbackRequest request) {
        Feedback feedback = new Feedback();
        feedback.setAppointmentId(request.getAppointmentId());
        feedback.setMobile(normalizeMobile(request.getMobile()));
        feedback.setFullName(request.getFullName());
        feedback.setRating(request.getRating());
        feedback.setFeedbackType(request.getFeedbackType());
        feedback.setComments(request.getComments());

        Feedback saved = feedbackRepository.save(feedback);
        return toResponse(saved);
    }

    public List<FeedbackResponse> getByMobile(String mobile) {
        String normalized = normalizeMobile(mobile);
        return feedbackRepository.findByMobileOrderByCreatedAtDesc(normalized)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<FeedbackResponse> getByAppointmentId(Long appointmentId) {
        return feedbackRepository.findByAppointmentIdOrderByCreatedAtDesc(appointmentId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<FeedbackResponse> getAllFeedbacks() {
        return feedbackRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private String normalizeMobile(String mobile) {
        if (mobile == null) return "";
        String digits = mobile.replaceAll("\\D", "");
        if (digits.length() > 10) {
            digits = digits.substring(digits.length() - 10);
        }
        return digits;
    }

    private FeedbackResponse toResponse(Feedback feedback) {
        return new FeedbackResponse(
                feedback.getId(),
                feedback.getAppointmentId(),
                feedback.getMobile(),
                feedback.getFullName(),
                feedback.getRating(),
                feedback.getFeedbackType(),
                feedback.getComments(),
                feedback.getCreatedAt() != null ? feedback.getCreatedAt().toString() : null
        );
    }
}
