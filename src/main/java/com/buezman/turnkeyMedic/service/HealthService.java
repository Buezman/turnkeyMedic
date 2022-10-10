package com.buezman.turnkeyMedic.service;

import com.buezman.turnkeyMedic.DiagnosisRequest;
import com.buezman.turnkeyMedic.entity.Diagnosis;
import com.buezman.turnkeyMedic.entity.SymptomDiagnosis;
import com.buezman.turnkeyMedic.entity.Symptom;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface HealthService {
    List<Symptom> getAllSymptoms() throws JsonProcessingException;
    List<SymptomDiagnosis> getDiagnosis(DiagnosisRequest request) throws JsonProcessingException;
    String updateDiagnosisValidity(Long diagnosisId);
    List<SymptomDiagnosis> getDiagnosisById(Long diagnosisId);

    List<Diagnosis> getAllDiagnoses();
}
