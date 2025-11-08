package com.clinic.repository;


import com.clinic.model.DoctorOtp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorOtpRepository extends JpaRepository<DoctorOtp, Long> {
  List<DoctorOtp> findTop5ByMobileOrderByIdDesc(String mobile);
}