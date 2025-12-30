package com.example.wessam.DTO.IN;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CoachDTOIn {

    @NotEmpty(message = "coach phone number must be entered")
    @Pattern(regexp = "^05\\d{8}$", message = "phone number must start with 05")
    private String phoneNumber;


    @NotNull(message = "birth date must be entered")
    @Past(message = "birth date must be on the past")
    private LocalDate birthDate;

    private String name;

    @NotNull(message = "years of experience must be entered")
    @Positive(message = "years of experience must be positive number")
    private Integer yearsOfExperience;

    @NotNull
    private Integer sportId;

    @NotNull
    private Integer branchId;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty(message = "username must be filled")
    @Size(min = 4, max = 20, message = "username size must be at least 4 maximum 20")
    @Pattern(regexp = "^[A-Za-z0-9]*$", message = "username must contain only letters and numbers")
    private String username;

    @NotEmpty(message = "password must be filled")
    @Size(min = 8, message = "password must be at least 8 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$",
            message = "password must contain at least one uppercase, one lowercase, one number and one special character")
    private String password;

    @Email
    @NotNull
    private String email;

}
