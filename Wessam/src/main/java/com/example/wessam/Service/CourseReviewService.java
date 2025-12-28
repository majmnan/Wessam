package com.example.wessam.Service;

import com.example.wessam.Api.ApiException;
import com.example.wessam.DTO.IN.CourseReviewDTOIn;
import com.example.wessam.DTO.OUT.CourseReviewDTOOut;
import com.example.wessam.Model.CourseRegistration;
import com.example.wessam.Model.CourseReview;
import com.example.wessam.Repository.CourseRegistrationRepository;
import com.example.wessam.Repository.CourseReviewRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseReviewService {

    private final CourseReviewRepository courseReviewRepository;
    private final CourseRegistrationRepository courseRegistrationRepository;
    private final ModelMapper mapper;

    //Auth: any
    public List<CourseReviewDTOOut> getCourseReviews(Integer courseId){
        return courseReviewRepository.findCourseReviewsByRegistration_Course_Id(courseId).stream().map(cr ->
        {
            CourseReviewDTOOut dto = mapper.map(cr, CourseReviewDTOOut.class);
            dto.setCourseName(cr.getRegistration().getCourse().getName());
            return dto;
        }).toList();
    }

    //Auth: Trainee
    public void reviewACourse(Integer traineeId, CourseReviewDTOIn dto){
        CourseRegistration registration = courseRegistrationRepository.findCourseRegistrationById(dto.getCourseRegistrationId());
        if(registration == null)
            throw new ApiException("course registration not found");

        if(!registration.getTrainee().getId().equals(traineeId))
            throw new ApiException("unAuthorized");

        courseReviewRepository.save(new CourseReview(null,dto.getRate(),dto.getDescription(),registration));
    }
    // Auth: Trainee
    public void updateReview(Integer traineeId, Integer reviewId, CourseReviewDTOIn dto) {
        CourseReview review = courseReviewRepository.findCourseReviewById(reviewId);

        if (!review.getRegistration().getTrainee().getId().equals(traineeId))
            throw new ApiException("unAuthorized");

        review.setRate(dto.getRate());
        review.setDescription(dto.getDescription());
        courseReviewRepository.save(review);
    }

    // Auth: Trainee
    public void deleteReview(Integer traineeId, Integer reviewId) {
        CourseReview review = courseReviewRepository.findCourseReviewById(reviewId);

        if (!review.getRegistration().getTrainee().getId().equals(traineeId))
            throw new ApiException("unAuthorized");

        courseReviewRepository.delete(review);
    }
}
