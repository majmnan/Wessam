package com.example.wessam.DTO.IN;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseReviewDTOIn {
    @NotNull
    @Min(1)
    @Max(5)
    private Integer rate;

    private String description;

    private Integer CourseRegistrationId;
}
