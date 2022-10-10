package com.buezman.turnkeyMedic.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Issue {

    @JsonProperty(value = "ID")
    private Long id;

    @JsonProperty(value = "Name")
    private String name;

    @JsonProperty(value = "Accuracy")
    private float accuracy;

    @JsonProperty(value = "Icd")
    private String icd;

    @JsonProperty(value = "IcdName")
    private String icdName;

    @JsonProperty(value = "ProfName")
    private String profName;

    @JsonProperty(value = "Ranking")
    private String ranking;

}
