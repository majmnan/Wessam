package com.example.wessam.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "name must be filled")
    @Pattern(regexp = "^[A-Z a-z]*$",message = "name must contain only litters")
    @Size(min = 2,max = 20,message = "name size must be at least 2 maximum 20")
    @Column(nullable = false,length = 20)
    private String name;

    @NotNull(message = "reward must be entered ")
    @Positive(message = "reward value must be a positive number")
    @Column(nullable = false)
    private Integer reward;

    @FutureOrPresent(message = "start date can't be in the past")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "start date must be entered")
    @Column(nullable = false)
    private LocalDate startDate;

    @FutureOrPresent(message = "end date can't be in the past")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "end date must be entered")
    @Column(nullable = false)
    private LocalDate endDate;

    @ManyToOne
    private Organizer organizer;

    @ManyToOne
    private Sport sport;

    @ManyToOne
    private Trainee champion;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "tournament")
    @JsonIgnore
    private Set<RegisteredTournament> registeredTournaments;

}
