package com.example.wessam.Configue;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

@org.springframework.context.annotation.Configuration
@EnableWebSecurity
public class Configuration {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebClient openaiClient() {
        return WebClient.builder()
                .baseUrl("https://api.openai.com")
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        return security
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/sport/add","/api/v1/sport/update/","/api/v1/sport/delete/").hasAuthority("ADMIN")
                        .requestMatchers("/api/v1/sport/").permitAll()
                        .requestMatchers("/api/v1/branches/get-all").hasAuthority("ADMIN")
                        .requestMatchers("/api/v1/branches/").hasAuthority("GYM")
                        .requestMatchers("/api/v1/coach/register","/api/v1/coach/update","/api/v1/coach/delete","/api/v1/coach/dashboard/").hasAuthority("COACH")
                        .requestMatchers("/api/v1/coach/activate/","/api/v1/coach/available/").hasAuthority("GYM")
                        .requestMatchers("/api/v1/coach/").permitAll()
                        .requestMatchers("/api/v1/gym/get","/api/v1/gym/activate/","/api/v1/gym/get-inactive","/api/v1/gym/deactivate/").hasAuthority("ADMIN")
                        .requestMatchers("/api/v1/gym/register","/api/v1/gym/report","/api/v1/gym/get-active","/api/v1/gym/complete-payment/").permitAll()
                        .requestMatchers("/api/v1/gym/").hasAuthority("GYM")
                        .requestMatchers("/api/v1/course/get","/api/v1/course/top","/api/v1/course/get/dateRange/","/api/v1/course/review-summary/","/api/v1/course/get/upcoming").permitAll()
                        .requestMatchers("/api/v1/course/get/recommended/","/api/v1/course/next-level-courses").hasAuthority("TRAINEE")
                        .requestMatchers("/api/v1/course/").hasAuthority("GYM")
                        .requestMatchers("/api/v1/course-registration/get-by-course/","/api/v1/course-registration/complete/","/api/v1/course-registration/drop/").hasAuthority("COACH")
                        .requestMatchers("/api/v1/course-registration/completed","/api/v1/course-registration/complete-payment/","/api/v1/course-registration/get/totalTrainee/").permitAll()
                        .requestMatchers("/api/v1/course-registration/dropped").hasAuthority("ADMIN")
                        .requestMatchers("/api/v1/course-registration/").hasAuthority("TRAINEE")
                        .requestMatchers("/api/v1/organizer/get").hasAuthority("ADMIN")
                        .requestMatchers("/api/v1/organizer/").hasAuthority("ORGANIZER")
                        .requestMatchers("/api/v1/tournament/generate-post/","/api/v1/tournament/get/dateRange/","/api/v1/tournament/get/upcoming").permitAll()
                        .requestMatchers("/api/v1/tournament/").hasAuthority("ORGANIZER")
                        .requestMatchers("/api/v1/registeredTournament/get","/api/v1/registeredTournament/get/totalTrainee/").hasAuthority("ADMIN")
                        .requestMatchers("/api/v1/tournament/").hasAuthority("TRAINEE")
                        .requestMatchers("/api/v1/trainee/get").hasAuthority("ADMIN")
                        .requestMatchers("/api/v1/trainee/register").permitAll()
                        .requestMatchers("/api/v1/trainee/").hasAuthority("TRAINEE")
                        .requestMatchers("/api/v1/trainee-feedback/add").hasAuthority("COACH")
                        .requestMatchers("/api/v1/trainee-feedback/get").hasAuthority("TRAINEE")
                        .requestMatchers("/api/v1/trainee/nutrition").hasAuthority("TRAINEE")







                        .anyRequest().permitAll()
                )

                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .httpBasic(basic -> {})

                .build();
    }
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    };

}
