package com.example.wessam.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class Sport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotEmpty(message = "sport name must be entered")
    @Pattern(regexp =  "^[a-zA-Z]+$",message = "sport name must be only letters")
    @Size(min = 2,max = 25,message = "sport name must be at least size of 2 and maximum size of 25")
    @Column(columnDefinition = "varchar(25) not null")
    private String name;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "sport")
    @JsonIgnore
    private Set<Tournament> tournaments;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "sport")
    @JsonIgnore
    private Set<Trainee> trainees;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "sport")
    @JsonIgnore
    private Set<Coach> coaches;

}
