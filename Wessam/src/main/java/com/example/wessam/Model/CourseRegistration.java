package com.example.wessam.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//todo: unique(course_id, trainee_id)
public class CourseRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne
    private Course course;


    @ManyToOne
    private Trainee trainee;



    @OneToMany(cascade = CascadeType.ALL,mappedBy = "registeredCourse")
    @JsonIgnore
    private Set<TraineeFeedback> traineeFeedbacks;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "registeredCourse")
    @JsonIgnore
    private Set<CoachReview> coachReviewses;
}
