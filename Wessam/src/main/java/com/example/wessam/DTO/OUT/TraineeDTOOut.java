package com.example.wessam.DTO.OUT;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TraineeDTOOut {
    private String userName;
    private LocalDate birthDate;
    private String gender;
    private Integer height;
    private Integer weight;
    private String level;
}
