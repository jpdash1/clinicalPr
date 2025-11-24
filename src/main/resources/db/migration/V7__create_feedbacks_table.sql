-- Create feedbacks table for user feedback feature
CREATE TABLE feedbacks (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    appointment_id BIGINT NULL,
    mobile VARCHAR(16) NOT NULL,
    full_name VARCHAR(200) NOT NULL,
    rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
    feedback_type VARCHAR(32) NOT NULL,
    comments VARCHAR(1000) NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_mobile (mobile),
    INDEX idx_appointment_id (appointment_id),
    INDEX idx_created_at (created_at)
);
