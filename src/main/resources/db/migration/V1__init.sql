-- Clinics and Appointments tables

CREATE TABLE clinics (
                         id VARCHAR(64) PRIMARY KEY,
                         clinic VARCHAR(255) NOT NULL,
                         location VARCHAR(255) NOT NULL,
                         schedule_label VARCHAR(255) NOT NULL,
                         rule_type VARCHAR(32) NOT NULL,           -- weekdays | firstSunday | lastSunday | lastSaturday | secondWeekend
                         weekdays VARCHAR(32) NULL                 -- comma-separated ints 0..6; only for rule_type=weekdays
);

CREATE TABLE appointments (
                              id BIGINT PRIMARY KEY AUTO_INCREMENT,
                              clinic_id VARCHAR(64) NOT NULL,
                              clinic VARCHAR(255) NOT NULL,
                              location VARCHAR(255) NOT NULL,
                              appointment_date DATE NOT NULL,
                              mobile VARCHAR(16) NOT NULL,
                              full_name VARCHAR(200) NOT NULL,
                              gender VARCHAR(16) NOT NULL,              -- Male/Female/Other
                              dob DATE NOT NULL,
                              city VARCHAR(150) NOT NULL,
                              district VARCHAR(150) NOT NULL,
                              state VARCHAR(150) NOT NULL,
                              created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              CONSTRAINT fk_appt_clinic FOREIGN KEY (clinic_id) REFERENCES clinics(id)
);

-- Seed clinics to match your UI
INSERT INTO clinics (id, clinic, location, schedule_label, rule_type, weekdays) VALUES
                                                                                    ('kims',      'KIMS Hospital', 'Bhubaneswar', 'Mon, Wed, Fri · 9:00 AM – 4:00 PM', 'weekdays', '1,3,5'),
                                                                                    ('neuron',    'Neuron Brain & Spine Clinic', 'Chandrasekharpur, Bhubaneswar', 'Every Tuesday & Thursday · 6:30 PM – 9:30 PM', 'weekdays', '2,4'),
                                                                                    ('astha',     'Astha Diagnostics', 'Midnapore, West Bengal', '2nd Saturday & Sunday (Every Month) · 9:00 AM – 5:00 PM', 'secondWeekend', NULL),
                                                                                    ('healthzone','The Health Zone', 'Balasore', 'Last Sunday (Every Month) · 9:00 AM – 2:00 PM', 'lastSunday', NULL),
                                                                                    ('rameswar',  'Rameswar Clinic', 'Angul, Odisha', '1st Sunday (Every Month) · 9:00 AM – 1:00 PM', 'firstSunday', NULL),
                                                                                    ('apollo',    'Apollo Diagnostics', 'Keshiyari More Rail Gate, Belda, West Bengal', 'Last Saturday (Every Month) · 9:00 AM – 2:00 PM', 'lastSaturday', NULL);