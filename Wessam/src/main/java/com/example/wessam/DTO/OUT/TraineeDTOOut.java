package com.example.wessam.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TraineeDTOOut {
    private String userName;
    private LocalDate birthDate;
    private String gender;
    private Double height;
    private Double weight;
    private String belt;
}
