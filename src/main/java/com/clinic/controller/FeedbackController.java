package com.clinic.controller;

import com.clinic.dto.FeedbackRequest;
import com.clinic.dto.FeedbackResponse;
import com.clinic.service.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
@CrossOrigin(origins = "http://localhost:5173")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping
    public ResponseEntity<FeedbackResponse> createFeedback(@Valid @RequestBody FeedbackRequest request) {
        FeedbackResponse response = feedbackService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/mobile/{mobile}")
    public ResponseEntity<List<FeedbackResponse>> getFeedbacksByMobile(@PathVariable String mobile) {
        List<FeedbackResponse> feedbacks = feedbackService.getByMobile(mobile);
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<List<FeedbackResponse>> getFeedbacksByAppointment(@PathVariable Long appointmentId) {
        List<FeedbackResponse> feedbacks = feedbackService.getByAppointmentId(appointmentId);
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping
    public ResponseEntity<List<FeedbackResponse>> getAllFeedbacks() {
        List<FeedbackResponse> feedbacks = feedbackService.getAllFeedbacks();
        return ResponseEntity.ok(feedbacks);
    }
}
