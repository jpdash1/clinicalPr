package com.clinic.controller;

import com.clinic.dto.FeedbackRequest;
import com.clinic.dto.FeedbackResponse;
import com.clinic.service.FeedbackService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FeedbackController.class)
@Import(TestSecurityConfig.class)
class FeedbackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedbackService feedbackService;

    @Autowired
    private ObjectMapper objectMapper;

    private FeedbackRequest mockRequest;
    private FeedbackResponse mockResponse;

    @BeforeEach
    void setUp() {
        mockRequest = new FeedbackRequest();
        mockRequest.setAppointmentId(100L);
        mockRequest.setMobile("9876543210");
        mockRequest.setFullName("John Doe");
        mockRequest.setRating(5);
        mockRequest.setFeedbackType("overall");
        mockRequest.setComments("Excellent service!");

        mockResponse = new FeedbackResponse(
                1L,
                100L,
                "9876543210",
                "John Doe",
                5,
                "overall",
                "Excellent service!",
                "2024-11-24 10:00:00"
        );
    }

    @Test
    void testCreateFeedback() throws Exception {
        when(feedbackService.create(any(FeedbackRequest.class))).thenReturn(mockResponse);

        mockMvc.perform(post("/api/feedbacks")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.mobile").value("9876543210"))
                .andExpect(jsonPath("$.fullName").value("John Doe"))
                .andExpect(jsonPath("$.rating").value(5))
                .andExpect(jsonPath("$.feedbackType").value("overall"));
    }

    @Test
    void testCreateFeedbackWithInvalidRating() throws Exception {
        mockRequest.setRating(6); // Invalid rating

        mockMvc.perform(post("/api/feedbacks")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetFeedbacksByMobile() throws Exception {
        List<FeedbackResponse> feedbacks = Arrays.asList(mockResponse);
        when(feedbackService.getByMobile("9876543210")).thenReturn(feedbacks);

        mockMvc.perform(get("/api/feedbacks/mobile/9876543210"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].mobile").value("9876543210"));
    }

    @Test
    void testGetFeedbacksByAppointment() throws Exception {
        List<FeedbackResponse> feedbacks = Arrays.asList(mockResponse);
        when(feedbackService.getByAppointmentId(100L)).thenReturn(feedbacks);

        mockMvc.perform(get("/api/feedbacks/appointment/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].appointmentId").value(100));
    }

    @Test
    void testGetAllFeedbacks() throws Exception {
        List<FeedbackResponse> feedbacks = Arrays.asList(mockResponse);
        when(feedbackService.getAllFeedbacks()).thenReturn(feedbacks);

        mockMvc.perform(get("/api/feedbacks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1));
    }
}
