package com.buezman.turnkeyMedic;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiagnosisRequest {
    private List<Integer> symptomsIds;
    private String gender;
    private int yearOfBirth;
}
