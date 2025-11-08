package com.clinic.controller;

import com.clinic.service.DoctorAuthService;
import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth/doctor")
@CrossOrigin(origins = "http://localhost:5173")
public class DoctorAuthController {

  private final DoctorAuthService service;

  public DoctorAuthController(DoctorAuthService service) {
    this.service = service;
  }

  @PostMapping("/request-otp")
  public ResponseEntity<?> requestOtp(@RequestBody Map<String,String> body) {
    String mobile = body.getOrDefault("mobile", "");
    try {
      service.requestOtp(mobile);
      return ResponseEntity.ok(Map.of("status","sent"));
    } catch (ValidationException e) {
      return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
  }

  @PostMapping("/verify-otp")
  public ResponseEntity<?> verify(@RequestBody Map<String,String> body) {
    String mobile = body.getOrDefault("mobile","");
    String code = body.getOrDefault("code","");
    try {
      String token = service.verifyOtp(mobile, code);
      return ResponseEntity.ok(Map.of("token", token));
    } catch (ValidationException e) {
      return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
  }
}