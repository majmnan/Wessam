package com.example.wessam.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class Gym {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotEmpty(message = "businuiss certificate id must be entered")
    @Size(max = 10,message = "businuiss certificate id  must be maximum  size of 10")
    @Column(columnDefinition = "varchar(10) not null unique")
    private String businuissCertificateId;


    @NotEmpty(message = "gym description must be entered")
    @Size(max = 250,message = "gym description must be maximum  size of 250")
    @Column(columnDefinition = "varchar(250) not null ")
    private String description;
}
