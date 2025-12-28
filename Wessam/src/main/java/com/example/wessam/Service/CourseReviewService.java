package com.example.wessam.Service;

import com.example.wessam.Api.ApiException;
import com.example.wessam.DTO.IN.CourseReviewDTOIn;
import com.example.wessam.Model.CourseRegistration;
import com.example.wessam.Model.CourseReview;
import com.example.wessam.Repository.CourseRegistrationRepository;
import com.example.wessam.Repository.CourseReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseReviewService {

    private final CourseReviewRepository courseReviewRepository;
    private final CourseRegistrationRepository courseRegistrationRepository;

    //Auth: any
    public List<CourseReview> getCourseReviews(Integer CourseId){
        return courseReviewRepository.findAll();
    }

    //Auth: Trainee
    public void reviewACourse(Integer traineeId, CourseReviewDTOIn dto){
        CourseRegistration registration = courseRegistrationRepository.findRegisteredCourseById(dto.getCourseRegistrationId());
        if(registration == null)
            throw new ApiException("course registration not found");

        if(!registration.getTrainee().getId().equals(traineeId))
            throw new ApiException("unAuthorized");

        courseReviewRepository.save(new CourseReview(null,dto.getRate(),dto.getDescription(),registration));
    }


}
