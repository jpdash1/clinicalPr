INSERT INTO doctors (full_name, mobile, active)
SELECT 'Dr. Santosh Kumar Dash', '9876543210', 1
    WHERE NOT EXISTS (
  SELECT 1 FROM doctors WHERE mobile = '9876543210'
);