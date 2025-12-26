package com.example.wessam.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//todo: unique(course_id, trainee_id)
public class RegisteredCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne
    @JoinColumn(name = "course_id_int", referencedColumnName = "id")
    @JsonIgnore
    private Course course;


    @ManyToOne
    @JoinColumn(name = "trainee_id_int", referencedColumnName = "id")
    @JsonIgnore
    private Trainee trainee;


    @OneToOne(cascade = CascadeType.ALL,mappedBy = "registeredCourse")
    @PrimaryKeyJoinColumn
    private CoachReviews coachReviews;


    @ManyToOne
    @JoinColumn(name = "traineeFeedback", referencedColumnName = "id")
    @JsonIgnore
    private TraineeFeedback traineeFeedback;
}
