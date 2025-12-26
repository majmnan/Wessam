package com.example.wessam.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.util.Set;

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


    @ManyToOne
    private Gym gym;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "branch")
    @JsonIgnore
    private Set<Coach> Coaches;

}
