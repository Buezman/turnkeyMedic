package com.buezman.turnkeyMedic.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SymptomDiagnosis {

    @JsonProperty("ID")
    private Long id;

    @JsonProperty(value = "Issue")
    private Issue issue;

    @JsonProperty(value = "Specialisation")
    private List<Specialization> specialization;

}
