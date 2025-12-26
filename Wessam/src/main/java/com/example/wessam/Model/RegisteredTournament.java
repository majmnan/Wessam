package com.example.wessam.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class RegisteredTournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne
    private Trainee trainee;


    @ManyToOne
    private Tournament tournament;
}
