package com.buezman.turnkeyMedic.controller;

import com.buezman.turnkeyMedic.DiagnosisRequest;
import com.buezman.turnkeyMedic.entity.*;
import com.buezman.turnkeyMedic.service.HealthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/health")
@RequiredArgsConstructor
public class HealthController {

    private final HealthService healthService;

    @GetMapping("symptoms")
    public List<Symptom> getAllSymptoms() throws JsonProcessingException {
        return healthService.getAllSymptoms();
    }

    @GetMapping("diagnose")
    public List<SymptomDiagnosis> getDiagnosis(@RequestBody DiagnosisRequest request) throws JsonProcessingException {
        return healthService.getDiagnosis(request);
    }

    @PutMapping("diagnoses/update/{diagnosisId}")
    public String updateDiagnosisValidity(@PathVariable Long diagnosisId) {
        return healthService.updateDiagnosisValidity(diagnosisId);
    }

    @GetMapping("diagnoses/{id}")
    public List<SymptomDiagnosis> getDiagnosisById(@PathVariable(name = "id") Long diagnosisId) {
        return healthService.getDiagnosisById(diagnosisId);
    }

    @GetMapping("diagnoses")
    public List<Diagnosis> getAllDiagnosis() {
        return healthService.getAllDiagnoses();
    }
}
