package com.example.wessam.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CoachDashboardDTOOut {
    private Integer CoursesCount;
    private Integer traineeCount;
    private Double aveRatings;
    private Integer yearsOfExpiernce;
}
