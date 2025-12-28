package com.example.wessam.DTO.IN;

import com.example.wessam.Model.CourseRegistration;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TraineeFeedbackDTOIn {
    @NotNull
    @Min(value = 1,message = "min rate must be 1")
    @Max(value = 5,message = "max rate must be 5")
    private Integer rate;

    @NotEmpty(message = "description must be entered")
    private String description;

    @NotNull(message = "date must be entered")
    private LocalDate date;

    @NotNull
    private Integer registrationId;
}
