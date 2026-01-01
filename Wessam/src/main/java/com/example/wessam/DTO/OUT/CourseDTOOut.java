package com.example.wessam.DTO.OUT;

import jakarta.persistence.Column;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.convert.DataSizeUnit;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTOOut {
    private Integer id;
    private String name;

    private String entryLevel;

    private LocalDate startDate;

    private LocalDate endDate;

    private String coachName;

    private String location;

    private String gymName;

    private Integer price;
}
