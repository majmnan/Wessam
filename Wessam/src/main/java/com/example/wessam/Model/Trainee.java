package com.example.wessam.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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

}
