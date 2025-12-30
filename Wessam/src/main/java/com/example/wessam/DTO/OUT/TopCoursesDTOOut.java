package com.example.wessam.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopCoursesDTOOut {
    private String courseName;
    private String coachName;
    private Integer traineesCount;
    private Double aveCourseRatings;

}
