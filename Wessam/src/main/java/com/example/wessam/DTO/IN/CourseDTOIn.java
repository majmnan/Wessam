package com.example.wessam.DTO.IN;

import jakarta.persistence.Column;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CourseDTOIn {
    @NotNull
    private String name;

    @NotNull
    @Positive
    private Integer price;

    @Pattern(regexp = "^(beginners|intermediate|advanced)$")
    private String entryLevel;

    @FutureOrPresent(message = "start date can't be in the past")
    @NotNull(message = "start date must be entered")
    private LocalDate startDate;

    @FutureOrPresent(message = "end date can't be in the past")
    @NotNull(message = "end date must be entered")
    private LocalDate endDate;

    @NotNull
    private Integer coachId;

}
