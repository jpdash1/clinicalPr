-- Doctor table (one row per doctor/admin user)
CREATE TABLE doctors (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         full_name VARCHAR(150) NOT NULL,
                         mobile VARCHAR(16) NOT NULL UNIQUE,
                         active TINYINT(1) NOT NULL DEFAULT 1,
                         created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Pre-seed one doctor (adjust mobile & name)
INSERT INTO doctors (full_name, mobile, active) VALUES
    ('Dr. Santosh Kumar Dash', '9876543210', 1);

-- OTP table: transient codes
CREATE TABLE doctor_otps (
                             id BIGINT PRIMARY KEY AUTO_INCREMENT,
                             mobile VARCHAR(16) NOT NULL,
                             code VARCHAR(10) NOT NULL,
                             expires_at TIMESTAMP NOT NULL,
                             consumed TINYINT(1) NOT NULL DEFAULT 0,
                             attempts INT NOT NULL DEFAULT 0,
                             created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             INDEX idx_doctor_otps_mobile (mobile),
                             INDEX idx_doctor_otps_expires (expires_at)
);