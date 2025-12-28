package com.example.wessam.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TraineeFeedbackDTOOut {
    private String courseName;
    private String traineeName;
    private Integer rate;
    private String description;
    private LocalDate date;
}
