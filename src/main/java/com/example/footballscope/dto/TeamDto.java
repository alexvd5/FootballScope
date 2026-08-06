package com.example.footballscope.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamDto {
    private Long id;
    private String name;
    private String tla;
    private String crest;
}
