package com.buezman.turnkeyMedic.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Symptom {
    @JsonProperty(value = "ID")
    private Long id;

    @JsonProperty(value = "Name")
    private String name;
}
