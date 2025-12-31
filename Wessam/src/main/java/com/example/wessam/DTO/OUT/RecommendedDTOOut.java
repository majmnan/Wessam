package com.example.wessam.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RecommendedDTOOut {
    private String courseName;
    private String coachName;
    private String entryLevel;
    private LocalDate startDate;
}
