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

import java.time.LocalDate;
import java.util.Set;

@Entity
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class Gym {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotEmpty
    private String name;

    @NotEmpty(message = "business certificate id must be entered")
    @Size(max = 10,message = "business certificate id  must be maximum  size of 10")
    @Column(columnDefinition = "varchar(10) not null unique")
    private String businessCertificateId;

    @Pattern(regexp = "^(InActive|Pending|Active)$")
    private String status;

    private LocalDate subscriptionEndDate;

    @NotEmpty(message = "gym description must be entered")
    @Size(max = 250,message = "gym description must be maximum  size of 250")
    @Column(columnDefinition = "varchar(250) not null ")
    private String description;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "gym")
    @JsonIgnore
    private Set<Branch> branches;

    @OneToOne
    @JsonIgnore
    @MapsId
    private User user;
}
