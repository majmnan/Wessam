package com.example.wessam.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class Coach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "coach name must be entered")
    @Size(max = 10,message = "phone number must be maximum  size of 10")
    @Pattern(regexp = "^05\\d{8}$", message = "phone number must start with 05")
    @Column(columnDefinition = "varchar(10) not null unique")
    private String phoneNumber;


    private LocalDate birthDate;


    @NotNull(message = "years of experience must be entered")
    @Positive(message = "years of experience must be positive number")
    @Column(columnDefinition = "int not null")
    private Integer yearsOfExperience;

    @ManyToOne
    @JoinColumn(name = "branch_id_int", referencedColumnName = "id")
    @JsonIgnore
    private Branch Branch;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "course")
    private Set<Course> courses;


    @ManyToOne
    @JoinColumn(name = "sport_id_int", referencedColumnName = "id")
    @JsonIgnore
    private Sport sport;


}
