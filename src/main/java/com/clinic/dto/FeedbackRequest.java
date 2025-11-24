package com.clinic.dto;

import jakarta.validation.constraints.*;

public class FeedbackRequest {
    
    private Long appointmentId;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[\\d\\s\\-\\+\\(\\)]+$", message = "Mobile number contains invalid characters")
    @Size(min = 10, max = 20, message = "Mobile number must be between 10 and 20 characters")
    private String mobile;

    @NotBlank(message = "Full name is required")
    @Size(max = 200, message = "Name cannot exceed 200 characters")
    private String fullName;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be between 1 and 5")
    @Max(value = 5, message = "Rating must be between 1 and 5")
    private Integer rating;

    @NotBlank(message = "Feedback type is required")
    @Pattern(regexp = "^(appointment|doctor|clinic|overall)$", message = "Invalid feedback type")
    private String feedbackType;

    @Size(max = 1000, message = "Comments cannot exceed 1000 characters")
    private String comments;

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(String feedbackType) {
        this.feedbackType = feedbackType;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
