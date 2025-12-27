package com.example.wessam.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
    private Integer height;
    private Integer weight;
    private String level;

    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;

    @NotEmpty
    private String name;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "trainee")
    @JsonIgnore
    private Set<CourseRegistration> coursRegistrations;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "champion")
    @JsonIgnore
    private Set<Tournament> tournaments;


    @ManyToOne
    private Sport sport;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "trainee")
    @JsonIgnore
    private Set<RegisteredTournament> registeredTournaments;



}
