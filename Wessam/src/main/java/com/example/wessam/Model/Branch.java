package com.example.wessam.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    private String city;

    @URL
    @Pattern(regexp = "^https://www.google.com/maps.*$")
    private String Location;

    //todo: many to one with gym
    //todo: one to many with coach
}
