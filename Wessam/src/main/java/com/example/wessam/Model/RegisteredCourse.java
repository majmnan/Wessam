package com.example.wessam.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    //todo: many to one courses
    //todo: many to one trainee

    //todo: one to one CoachReviews
    //todo: many to one traineeFeedback
}
