package com.example.wessam.DTO.OUT;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CoachDTOOut {
    private String name;

    private String phoneNumber;

    private LocalDate birthDate;

    private Integer yearsOfExperience;

    private String sport;
}
