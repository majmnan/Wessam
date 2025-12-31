package com.example.wessam.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TraineeFeedbackDTOOut {
    private String courseName;
    private String traineeName;
    private Integer rate;
    private String description;
    private LocalDate date;
}
