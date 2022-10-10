package com.buezman.turnkeyMedic.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class Specialization {
    @JsonProperty(value = "ID")
    private Integer id;
    @JsonProperty(value = "Name")
    private String name;
    @JsonProperty(value = "SpecialistID")
    @JoinColumn(name = "post_id", nullable = false)
    private String specialistId;
}
