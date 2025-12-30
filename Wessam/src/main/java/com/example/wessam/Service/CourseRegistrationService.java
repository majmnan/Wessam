package com.example.wessam.Service;

import com.example.wessam.Api.ApiException;
import com.example.wessam.Api.ApiResponse;
import com.example.wessam.DTO.IN.CardDTOIn;
import com.example.wessam.DTO.IN.N8nPdfCertGenDTOIn;
import com.example.wessam.DTO.IN.PaymentRequestDTO;
import com.example.wessam.DTO.OUT.CourseRegistrationDTOOut;
import com.example.wessam.DTO.OUT.N8nPdfCertGenDtoOUT;
import com.example.wessam.DTO.OUT.PaymentResponseDTO;
import com.example.wessam.Model.Coach;
import com.example.wessam.DTO.OUT.TraineeCountDTOOut;
import com.example.wessam.Model.Course;
import com.example.wessam.Model.CourseRegistration;
import com.example.wessam.Model.RegisteredTournament;
import com.example.wessam.Model.Trainee;
import com.example.wessam.Repository.CourseRepository;
import com.example.wessam.Repository.CourseRegistrationRepository;
import com.example.wessam.Repository.TraineeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseRegistrationService {

    private final CourseRegistrationRepository courseRegistrationRepository;
    private final CourseRepository courseRepository;
    private final TraineeRepository traineeRepository;
    private final PaymentService paymentService;
    private final N8nService n8nService;


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

        ResponseEntity<PaymentResponseDTO> response = paymentService.processPayment(new PaymentRequestDTO(card, course.getPrice(), "SAR", String.valueOf(registration.getId()), "http://localhost:8080/api/v1/course-registration/complete-payment/"+registration.getId()));
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

        ResponseEntity<PaymentResponseDTO> response = paymentService.processPayment(new PaymentRequestDTO(card, courseRegistration.getCourse().getPrice(), "SAR", "pay", "http://localhost:8080/api/v1/course-registration/complete-payment/"+registrationId));
        if(!response.getStatusCode().is2xxSuccessful())
            throw new ResponseStatusException(response.getStatusCode(),response.hasBody() ? response.getBody().toString() : "");

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    public void checkPayment(Integer registrationId, String paymentId){
        ResponseEntity<PaymentResponseDTO> response = paymentService.getPayment(paymentId);
        if("paid".equals(response.getBody().getStatus())){
            CourseRegistration registration = courseRegistrationRepository.findCourseRegistrationById(registrationId);
            registration.setStatus("Registered");
            courseRegistrationRepository.save(registration);
        }
        else
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

    public List<Course> getCoursesRegestered(Integer traineeId) {
        Trainee trainee = traineeRepository.findTraineeById(traineeId);
        if (trainee == null) {
            throw new ApiException("Trainee not found");
        }
        return courseRegistrationRepository.findRegestedCourses(traineeId);
    }


    public TraineeCountDTOOut courseNumOfTrainees(Integer id){
        CourseRegistration courseRegistration = courseRegistrationRepository.findCourseRegistrationById(id);
        if(courseRegistration == null){
            throw new ApiException("no registration found");
        }
        Integer traineeCount= courseRegistrationRepository.TraineeCourseCount(id);
        return new TraineeCountDTOOut(traineeCount);
    }
    public void markAsCompleted(Integer userId, Integer regId) {
        CourseRegistration reg = courseRegistrationRepository.findCourseRegistrationById(regId);
        if (reg == null)
            throw new ApiException("Registration not found");

        if (!reg.getCourse().getCoach().getUser().getId().equals(userId)) {
            throw new ApiException("Unauthorized, You cannot drop this course");
        }
        if(reg.getCourse().getEndDate().isAfter(LocalDate.now()))
            throw new ApiException("course is not completed yet, end date is: "+reg.getCourse().getEndDate().toString());
        reg.setStatus("Complete");
        courseRegistrationRepository.save(reg);
    }

    public void markAsDropped(Integer userId, Integer regId) {
        CourseRegistration reg = courseRegistrationRepository.findCourseRegistrationById(regId);
        if (reg == null)
            throw new ApiException("Registration not found");

        if (!reg.getCourse().getCoach().getUser().getId().equals(userId))
            throw new ApiException("Unauthorized, You cannot drop this course");

        reg.setStatus("DROPPED");
        courseRegistrationRepository.save(reg);
    }

    //trainee auth
    public void generateCertificate(Integer traineeId, Integer courseRegId){
        CourseRegistration courseReg = courseRegistrationRepository.findCourseRegistrationById(courseRegId);
        Trainee trainee = traineeRepository.findTraineeById(traineeId);
        if(trainee == null)
            throw new ApiException("trainee not found");
        if (courseReg == null) {
            throw new ApiException("Course registration not found");
        }
        if (courseReg.getCourse().getEndDate().isAfter(LocalDate.now())) {
            throw new ApiException("Cannot issue certificate: course is not yet completed.");
        }
        if(!courseReg.getTrainee().getUser().getId().equals(traineeId)){
            throw new ApiException("UnAuthorized request ");
        }
        if(!"Complete".equalsIgnoreCase(courseReg.getStatus())){
            throw new ApiException("trainee doesn't complete the course");
        }
        N8nPdfCertGenDTOIn n8nRequest = new N8nPdfCertGenDTOIn(trainee.getName(),trainee.getEmail(),courseReg.getCourse().getName(),courseReg.getCourse().getEndDate().toString());
        System.out.println(n8nRequest);
        N8nPdfCertGenDtoOUT response = n8nService.triggerPdf(n8nRequest);
        if(!"Certificate was generated and sent successfully".equalsIgnoreCase(response.getMessage()))
            throw new ApiException("something went wrong "+response.getMessage());
    }

    public List<CourseRegistrationDTOOut> getCompletedRegistrations() {
        return courseRegistrationRepository.findAllByStatus("COMPLETED")
                .stream()
                .map(rc -> new CourseRegistrationDTOOut(rc.getTrainee().getName(), rc.getCourse().getName()))
                .toList();
    }

    public List<CourseRegistrationDTOOut> getDroppedRegistrations() {
        return courseRegistrationRepository.findAllByStatus("DROPPED")
                .stream()
                .map(rc -> new CourseRegistrationDTOOut(rc.getTrainee().getName(), rc.getCourse().getName()))
                .toList();
    }
}
