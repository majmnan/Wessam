package com.example.wessam.Service;

import com.example.wessam.Api.ApiException;
import com.example.wessam.Api.ApiResponse;
import com.example.wessam.DTO.IN.CardDTOIn;
import com.example.wessam.DTO.IN.PaymentRequestDTO;
import com.example.wessam.DTO.OUT.CourseRegistrationDTOOut;
import com.example.wessam.DTO.OUT.PaymentResponseDTO;
import com.example.wessam.Model.Course;
import com.example.wessam.Model.CourseRegistration;
import com.example.wessam.Model.Trainee;
import com.example.wessam.Repository.CourseRepository;
import com.example.wessam.Repository.CourseRegistrationRepository;
import com.example.wessam.Repository.TraineeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseRegistrationService {

    private final CourseRegistrationRepository courseRegistrationRepository;
    private final CourseRepository courseRepository;
    private final TraineeRepository traineeRepository;
    private final PaymentService paymentService;


    //Auth: Coach
    public List<CourseRegistrationDTOOut> getRegistrationsOfACourse(Integer coachId, Integer courseId){
        Course course = courseRepository.findCourseById(courseId);
        if(!course.getCoach().getId().equals(coachId))
            throw new ApiException("unAuthorized");

        return courseRegistrationRepository.findCourseRegistrationByCourseId(courseId)
                .stream().map( rc ->
                        new CourseRegistrationDTOOut(rc.getTrainee().getName(), rc.getCourse().getName())
                ).toList();
    }

    //Auth: Trainee
    public ResponseEntity<PaymentResponseDTO> registerInCourse(Integer traineeId, Integer courseId, CardDTOIn card){
        Course course = courseRepository.findCourseById(courseId);
        if(course == null)
            throw new ApiException("course not found");

        Trainee trainee = traineeRepository.findTraineeById(traineeId);

        if(!course.getEntryLevel().equals(trainee.getLevel()))
            throw new ApiException("this trainee is not in the same level of the course");

        CourseRegistration registration = courseRegistrationRepository.save(new CourseRegistration(null,course,trainee,"Pending",null,null));

        ResponseEntity<PaymentResponseDTO> response = paymentService.processPayment(new PaymentRequestDTO(card, course.getPrice(), "SAR", String.valueOf(registration.getId()), "http://localhost:8080/api/v1/course-registration/complete-payment"));
        if(!response.getStatusCode().is2xxSuccessful())
            throw new ResponseStatusException(response.getStatusCode(),response.hasBody() ? response.getBody().toString() : "");

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());

    }

    //Auth: Trainee
    public ResponseEntity<PaymentResponseDTO> payPendingRegistration(Integer traineeId, Integer registrationId, CardDTOIn card){
        CourseRegistration courseRegistration = courseRegistrationRepository.findCourseRegistrationById(registrationId);
        if(courseRegistration == null)
            throw new ApiException("no registration found");

        if(!courseRegistration.getTrainee().getId().equals(traineeId))
            throw new ApiException("unAuthorized");

        ResponseEntity<PaymentResponseDTO> response = paymentService.processPayment(new PaymentRequestDTO(card, courseRegistration.getCourse().getPrice(), "SAR", String.valueOf(registrationId), "http://localhost:8080/api/v1/complete-payment"));
        if(!response.getStatusCode().is2xxSuccessful())
            throw new ResponseStatusException(response.getStatusCode(),response.hasBody() ? response.getBody().toString() : "");

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    public void checkPayment(String paymentId){
        ResponseEntity<PaymentResponseDTO> response = paymentService.getPayment(paymentId);
        if("paid".equals(response.getBody().getStatus())){
            CourseRegistration registration = courseRegistrationRepository.findCourseRegistrationById(Integer.valueOf(response.getBody().getDescription()));
            registration.setStatus("Registered");
            courseRegistrationRepository.save(registration);
        }
        throw new ResponseStatusException(response.getStatusCode(),response.getBody().getSource().getMessage());
    }

    //Auth: Trainee
    public void deleteRegistration(Integer traineeId, Integer registeredCourseId){
        CourseRegistration courseRegistration = courseRegistrationRepository.findCourseRegistrationById(registeredCourseId);
        if(courseRegistration == null)
            throw new ApiException("no registration found");

        if(!courseRegistration.getTrainee().getId().equals(traineeId))
            throw new ApiException("unAuthorized");

        courseRegistrationRepository.delete(courseRegistration);
    }
}
