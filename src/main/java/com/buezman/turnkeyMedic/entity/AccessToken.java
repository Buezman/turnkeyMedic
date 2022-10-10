package com.buezman.turnkeyMedic.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
@Getter
@Setter
public class AccessToken {
    @JsonProperty(value = "Token")
    private String token;
    @JsonProperty(value = "ValidThrough")
    private int validThrough;
}
