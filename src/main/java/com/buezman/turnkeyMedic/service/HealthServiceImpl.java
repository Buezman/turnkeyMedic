package com.buezman.turnkeyMedic.service;

import com.buezman.turnkeyMedic.DiagnosisRequest;
import com.buezman.turnkeyMedic.entity.Diagnosis;
import com.buezman.turnkeyMedic.entity.SymptomDiagnosis;
import com.buezman.turnkeyMedic.entity.Symptom;
//import com.buezman.turnkeyMedic.repository.DiagnosisRepository;
import com.buezman.turnkeyMedic.exceptions.ResourceNotFoundException;
import com.buezman.turnkeyMedic.repository.DiagnosisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Slf4j
@Service
@RequiredArgsConstructor
public class HealthServiceImpl implements HealthService{

    private final AuthService authService;
    private final DiagnosisRepository diagnosisRepository;
    private final RestTemplate restTemplate;

    @Override
    public List<Symptom> getAllSymptoms() {
        String url = "https://sandbox-healthservice.priaid.ch/symptoms";
        String token = authService.generateAuthToken();
        //noinspection unchecked
        return restTemplate.exchange(String.format("%s?token=%s&format=json&language=en-gb",url,token),
                HttpMethod.GET, null,
                (Class<List<Symptom>>)(Class<?>)List.class).getBody();
    }

    @Override
    public List<SymptomDiagnosis> getDiagnosis(DiagnosisRequest request) {
        String url = "https://sandbox-healthservice.priaid.ch/diagnosis?symptoms";
        String symptomsString = String.valueOf(request.getSymptomsIds());
        String gender = request.getGender();
        int yearOfBirth = request.getYearOfBirth();
        String token = authService.generateAuthToken();

        List<SymptomDiagnosis> diagnoses = restTemplate.exchange(
                String.format("%s=%s&gender=%s&year_of_birth=%s&token=%s&format=json&language=en-gb",
                        url,symptomsString,gender,yearOfBirth,token),
                HttpMethod.GET,
                null,
                (Class<List<SymptomDiagnosis>>)(Class<?>)List.class).getBody();

        assert diagnoses != null;

        Diagnosis diagnosis = Diagnosis.builder()
                .symptomsIds(symptomsString)
                .gender(request.getGender())
                .yearOfBirth(request.getYearOfBirth())
                .build();
        diagnosisRepository.save(diagnosis);

        return diagnoses;

    }

    @Override
    public String updateDiagnosisValidity(Long diagnosisId) {
        Diagnosis diagnosis = diagnosisRepository.findById(diagnosisId)
                .orElseThrow(()-> new ResourceNotFoundException("diagnosis", "id", ""+diagnosisId));
        boolean validValue = diagnosis.isValid();
        String value = validValue ? "invalid" : "valid";
        diagnosis.setValid(!validValue);

        diagnosisRepository.save(diagnosis);

        return String.format("Diagnosis with id: '%s' updated to %s",diagnosisId, value);
    }

    @Override
    public List<SymptomDiagnosis> getDiagnosisById(Long diagnosisId) {
        String url = "https://sandbox-healthservice.priaid.ch/diagnosis?symptoms";
        Diagnosis diagnosis = diagnosisRepository.findById(diagnosisId)
                .orElseThrow(() -> new ResourceNotFoundException("Diagnosis", "ID", ""+diagnosisId));
        String token = authService.generateAuthToken();
        String symptomsIds = diagnosis.getSymptomsIds();
        String gender = diagnosis.getGender();
        int yearOfBirth = diagnosis.getYearOfBirth();

        return restTemplate.exchange(
                String.format("%s=%s&gender=%s&year_of_birth=%s&token=%s&format=json&language=en-gb",
                        url,symptomsIds,gender,yearOfBirth,token),
                HttpMethod.GET,
                null,
                (Class<List<SymptomDiagnosis>>)(Class<?>)List.class).getBody();
    }

    @Override
    public List<Diagnosis> getAllDiagnoses() {
        return diagnosisRepository.findAll();
    }

    private List<Integer> getSymptomsIdsAsList(String symptomIdsAsString) {
        symptomIdsAsString = symptomIdsAsString.substring(1, symptomIdsAsString.length()-1);
        List<Integer> list = new ArrayList<>();
        for (String s : symptomIdsAsString.split(",")) {
            list.add(Integer.parseInt(s));
        }
        return list;
    }

}
