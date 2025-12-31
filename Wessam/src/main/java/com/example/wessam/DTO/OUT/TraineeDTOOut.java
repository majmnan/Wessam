package com.example.wessam.DTO.OUT;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TraineeDTOOut {
    private String name;
    private LocalDate birthDate;
    private String gender;
    private Integer height;
    private Integer weight;
    private String level;
    private String email;

}
