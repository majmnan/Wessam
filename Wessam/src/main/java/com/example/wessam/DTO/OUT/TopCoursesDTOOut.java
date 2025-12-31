package com.example.wessam.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TopCoursesDTOOut {
    private String courseName;
    private String coachName;
    private Integer traineesCount;
    private Double aveCourseRatings;

}
