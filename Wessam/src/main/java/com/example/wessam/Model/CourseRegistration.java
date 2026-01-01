package com.example.wessam.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"course_id","trainee_user_id"}))
public class CourseRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Course course;


    @ManyToOne
    private Trainee trainee;

    @Pattern(regexp = "^(Pending|Registered|Fail|Complete)$")
    @Column(nullable = false)
    private String status;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "courseRegistration")
    @JsonIgnore
    private Set<TraineeFeedback> traineeFeedbacks;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "registration")
    @JsonIgnore
    private List<CourseReview> courseReviews;
}
