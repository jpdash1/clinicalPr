ALTER TABLE appointments
    ADD COLUMN IF NOT EXISTS doctor_id BIGINT,
    ADD COLUMN IF NOT EXISTS appointment_time TIME,
    ADD COLUMN IF NOT EXISTS duration_minutes INT DEFAULT 30,
    ADD COLUMN IF NOT EXISTS status VARCHAR(30) DEFAULT 'pending',
    ADD COLUMN IF NOT EXISTS visit_type VARCHAR(40);

CREATE INDEX IF NOT EXISTS idx_appt_doctor_date
    ON appointments (doctor_id, appointment_date);