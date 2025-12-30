package com.example.wessam.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CoachDashboardDTOOut {
    private Integer CoursesCount;
    private Integer traineeCount;
    private Double aveRatings;
    private Integer yearsOfExpiernce;
}
