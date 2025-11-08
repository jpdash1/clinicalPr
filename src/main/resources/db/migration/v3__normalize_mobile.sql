-- Normalize existing mobile numbers to last 10 digits (remove non-digits)
-- MySQL 8 has REGEXP_REPLACE
UPDATE appointments
SET mobile = RIGHT(REGEXP_REPLACE(mobile, '[^0-9]', ''), 10)
WHERE mobile IS NOT NULL;

-- Optional: add combined index (if not added yet) for fast lookup
CREATE INDEX IF NOT EXISTS idx_appointments_mobile_id ON appointments (mobile, id);