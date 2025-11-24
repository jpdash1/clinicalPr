package com.clinic.dto;

public class FeedbackResponse {
    private Long id;
    private Long appointmentId;
    private String mobile;
    private String fullName;
    private Integer rating;
    private String feedbackType;
    private String comments;
    private String createdAt;

    public FeedbackResponse(Long id, Long appointmentId, String mobile, String fullName, 
                           Integer rating, String feedbackType, String comments, String createdAt) {
        this.id = id;
        this.appointmentId = appointmentId;
        this.mobile = mobile;
        this.fullName = fullName;
        this.rating = rating;
        this.feedbackType = feedbackType;
        this.comments = comments;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
