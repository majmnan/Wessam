package com.example.wessam.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Pattern(regexp = "^(beginners|intermediate|advanced)$")
    @Column(nullable = false)
    private String entryLevel;
    @FutureOrPresent(message = "start date can't be in the past")
    @NotNull(message = "start date must be entered")
    @Column(nullable = false)
    private LocalDate startDate;
    @FutureOrPresent(message = "end date can't be in the past")
    @NotNull(message = "end date must be entered")
    @Column(nullable = false)
    private LocalDate endDate;


    //todo: connect the relations with the mising models(coach, trainee)
}
