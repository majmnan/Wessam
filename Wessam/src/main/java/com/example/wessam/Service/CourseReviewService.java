package com.example.wessam.Service;

import com.example.wessam.Api.ApiException;
import com.example.wessam.DTO.IN.CourseReviewDTOIn;
import com.example.wessam.DTO.OUT.CourseReviewDTOOut;
import com.example.wessam.Model.Course;
import com.example.wessam.Model.CourseRegistration;
import com.example.wessam.Model.CourseReview;
import com.example.wessam.Repository.CourseRegistrationRepository;
import com.example.wessam.Repository.CourseRepository;
import com.example.wessam.Repository.CourseReviewRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseReviewService {

    private final CourseReviewRepository courseReviewRepository;
    private final CourseRegistrationRepository courseRegistrationRepository;
    private final ModelMapper mapper;
    private final CourseRepository courseRepository;
    private final AiService aiService;

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

    //Auth: any
    public JsonNode CourseReviewSummary(Integer courseId){
        Course course = courseRepository.findCourseById(courseId);
        if(course == null)
            throw new ApiException("Course Not Found");

        if(course.getEndDate().isAfter(LocalDate.now()))
            throw new ApiException("course not done yet");

        String prompt =
                """
                    You are a professional course quality analyst and evaluator.
                    
                    You are given:
                    - Course details
                    - A list of course reviews written by trainees
                    
                    Your task:
                    - Carefully analyze ALL reviews together
                    - Identify the MOST important and MOST repeated insights
                    - Think like an experienced, objective course evaluator
                    - Focus on learning quality, structure, delivery, and outcomes
                    
                    Input:
                    - courseDetails : Course
                    - courseReviews : List<CourseReview>
                    
                    You MUST return the result in the EXACT JSON format below:
                    
                    {
                      "summaryForCourse": "<course.name>",
                      "avgReviews": double
                      "positives": [
                        "string",
                        "string",
                        "string"
                      ],
                      "negatives": [
                        "string",
                        "string",
                        "string"
                      ]
                    }
                    
                    Rules (STRICT):
                    - Return ONLY valid JSON
                    - Do NOT add explanations, comments, or extra fields
                    - Do NOT expose sensitive or personal data
                    - Each sentence must be:
                      - Clear
                      - Professional
                      - Direct
                      - Kind
                      - Maximum 4 words
                    - Insights must be:
                      - Repeated across multiple reviews OR
                      - Highly impactful for course quality
                    - Avoid vague statements (e.g. "good", "bad", "nice")
                    
                        courseDetails
                        """+
                        aiService.toJson(course)
                        +"""
                        
                        courseReviews:
                        """+
                        aiService.toJson(
                                courseReviewRepository.findCourseReviewsByRegistration_Course_Id(courseId).stream().map(r -> {
                                        CourseReviewDTOOut dto = mapper.map(r, CourseReviewDTOOut.class);
                                        dto.setCourseName(course.getName());
                                        return dto;
                                })
                        );

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(aiService.callAi(prompt));
    }
}
