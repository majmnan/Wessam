package com.example.wessam.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Trainee {
    @Id
    private Integer id;


    private LocalDate birthDate;
    private String gender;
    private Double height;//update: changed it to Double instead of String
    private Double weight;//update: changed it to Double instead of String
    private String belt;


    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "registeredCourse")
    private Set<RegisteredCourse> registeredCourses;


}
