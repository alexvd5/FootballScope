package com.example.footballscope.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class League {
    @Id
    private Long id;

    private String name;

    private String country;

    private String code;

    private String logo;

    private int season;
}
