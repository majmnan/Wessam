package com.example.wessam.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JoinColumn(name = "trainee_id_int", referencedColumnName = "id")
    @JsonIgnore
    private Trainee trainee;


    @ManyToOne
    @JoinColumn(name = "tournament_id_int", referencedColumnName = "id")
    @JsonIgnore
    private Tournament tournament;
}
