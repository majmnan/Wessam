package com.example.wessam.Service;

import com.example.wessam.Api.ApiException;
import com.example.wessam.DTO.IN.TraineeFeedbackDTOIn;
import com.example.wessam.DTO.OUT.CourseReviewDTOOut;
import com.example.wessam.DTO.OUT.TraineeFeedbackDTOOut;
import com.example.wessam.Model.Coach;
import com.example.wessam.Model.CourseRegistration;
import com.example.wessam.Model.TraineeFeedback;
import com.example.wessam.Repository.CourseRegistrationRepository;
import com.example.wessam.Repository.TraineeFeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TraineeFeedbackService {
    private final TraineeFeedbackRepository traineeFeedbackRepository;
    private final CourseRegistrationRepository courseRegistrationRepository;
    private final ModelMapper mapper;

    //Auth: trainee
    public List<TraineeFeedbackDTOOut> getFeedbacksByTraineeId(Integer traineeId){
        return traineeFeedbackRepository.findTraineeFeedbacksByCourseRegistration_Trainee_Id(traineeId)
                .stream().map(tf ->{
                    TraineeFeedbackDTOOut dto = mapper.map(tf,TraineeFeedbackDTOOut.class);
                    dto.setCourseName(tf.getCourseRegistration().getCourse().getName());
                    dto.setTraineeName(tf.getCourseRegistration().getTrainee().getName());
                    return dto;
                }).toList();
    }

    //Auth: Coach
    public void feedbackATrainee(Integer coachId, TraineeFeedbackDTOIn dto){
        CourseRegistration courseRegistration = courseRegistrationRepository.findCourseRegistrationById(dto.getRegistrationId());
        if(courseRegistration == null)
            throw new ApiException("course registration noy found");

        if(!coachId.equals(courseRegistration.getCourse().getCoach().getId()))
            throw new ApiException("unAuthorized");

        if(dto.getDate().isAfter(courseRegistration.getCourse().getEndDate()) || dto.getDate().isBefore(courseRegistration.getCourse().getStartDate()))
            throw new ApiException("feedback date should be for class period");

        traineeFeedbackRepository.save(
                new TraineeFeedback(null,dto.getRate(),dto.getDescription(),dto.getDate(),courseRegistration)
        );
    }



}
