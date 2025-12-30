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

    @NotEmpty
    private String name;

    @NotEmpty(message = "coach name must be entered")
    @Pattern(regexp = "^05\\d{8}$", message = "phone number must start with 9665")
    @Column(columnDefinition = "varchar(15) not null")
    private String phoneNumber;

    @NotNull
    @Past
    private LocalDate birthDate;


    @NotNull(message = "years of experience must be entered")
    @Positive(message = "years of experience must be positive number")
    @Column(columnDefinition = "int not null")
    private Integer yearsOfExperience;

    @NotNull
    @Pattern(regexp = "^(InActive|Active)$")
    private String status;

    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;

    @ManyToOne
    private Branch branch;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "coach")
    @JsonIgnore
    private Set<Course> courses;

    @ManyToOne
    private Sport sport;


}
