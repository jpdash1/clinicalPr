package com.clinic.service;

import com.clinic.dto.FeedbackRequest;
import com.clinic.dto.FeedbackResponse;
import com.clinic.model.Feedback;
import com.clinic.repository.FeedbackRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @InjectMocks
    private FeedbackService feedbackService;

    private Feedback mockFeedback;
    private FeedbackRequest mockRequest;

    @BeforeEach
    void setUp() {
        mockFeedback = new Feedback();
        mockFeedback.setId(1L);
        mockFeedback.setAppointmentId(100L);
        mockFeedback.setMobile("9876543210");
        mockFeedback.setFullName("John Doe");
        mockFeedback.setRating(5);
        mockFeedback.setFeedbackType("overall");
        mockFeedback.setComments("Excellent service!");

        mockRequest = new FeedbackRequest();
        mockRequest.setAppointmentId(100L);
        mockRequest.setMobile("9876543210");
        mockRequest.setFullName("John Doe");
        mockRequest.setRating(5);
        mockRequest.setFeedbackType("overall");
        mockRequest.setComments("Excellent service!");
    }

    @Test
    void testCreateFeedback() {
        when(feedbackRepository.save(any(Feedback.class))).thenReturn(mockFeedback);

        FeedbackResponse response = feedbackService.create(mockRequest);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("9876543210", response.getMobile());
        assertEquals("John Doe", response.getFullName());
        assertEquals(5, response.getRating());
        assertEquals("overall", response.getFeedbackType());
        assertEquals("Excellent service!", response.getComments());

        verify(feedbackRepository, times(1)).save(any(Feedback.class));
    }

    @Test
    void testGetByMobile() {
        List<Feedback> feedbacks = Arrays.asList(mockFeedback);
        when(feedbackRepository.findByMobileOrderByCreatedAtDesc("9876543210"))
                .thenReturn(feedbacks);

        List<FeedbackResponse> responses = feedbackService.getByMobile("9876543210");

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("9876543210", responses.get(0).getMobile());

        verify(feedbackRepository, times(1)).findByMobileOrderByCreatedAtDesc("9876543210");
    }

    @Test
    void testGetByAppointmentId() {
        List<Feedback> feedbacks = Arrays.asList(mockFeedback);
        when(feedbackRepository.findByAppointmentIdOrderByCreatedAtDesc(100L))
                .thenReturn(feedbacks);

        List<FeedbackResponse> responses = feedbackService.getByAppointmentId(100L);

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals(100L, responses.get(0).getAppointmentId());

        verify(feedbackRepository, times(1)).findByAppointmentIdOrderByCreatedAtDesc(100L);
    }

    @Test
    void testGetAllFeedbacks() {
        List<Feedback> feedbacks = Arrays.asList(mockFeedback);
        when(feedbackRepository.findAllByOrderByCreatedAtDesc()).thenReturn(feedbacks);

        List<FeedbackResponse> responses = feedbackService.getAllFeedbacks();

        assertNotNull(responses);
        assertEquals(1, responses.size());

        verify(feedbackRepository, times(1)).findAllByOrderByCreatedAtDesc();
    }

    @Test
    void testMobileNormalization() {
        mockRequest.setMobile("+91-987-654-3210");
        when(feedbackRepository.save(any(Feedback.class))).thenReturn(mockFeedback);

        FeedbackResponse response = feedbackService.create(mockRequest);

        assertNotNull(response);
        verify(feedbackRepository, times(1)).save(any(Feedback.class));
    }
}
