package com.example.wessam.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CoachReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Min(value = 1,message = "min rate must be 1")
    @Max(value = 5,message = "max rate must be 5")
    private Integer rate;

    @NotEmpty(message = "description must be entered")
    private String description;

    @ManyToOne
    private RegisteredCourse registeredCourse;

}