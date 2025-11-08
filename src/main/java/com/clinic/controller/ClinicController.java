package com.clinic.controller;


import com.clinic.model.Clinic;
import com.clinic.repository.ClinicRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clinics")
@CrossOrigin(origins = "http://localhost:5173")
public class ClinicController {
  private final ClinicRepository repo;
  public ClinicController(ClinicRepository repo) { this.repo = repo; }

  @GetMapping
  public List<Clinic> list() {
    return repo.findAll();
  }
}