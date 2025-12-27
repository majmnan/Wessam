package com.example.wessam.DTO.IN;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TraineeDTOIn {

    @NotEmpty(message = "username must be filled")
    @Size(min = 4, max = 20, message = "username size must be at least 4 maximum 20")
    @Pattern(regexp = "^[A-Za-z0-9]*$", message = "username must contain only letters and numbers")
    private String userName;

    @NotEmpty(message = "password must be filled")
    @Size(min = 8, message = "password must be at least 8 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$",
            message = "password must contain at least one uppercase, one lowercase, one number and one special character")
    private String password;

    @NotNull(message = "birth date must be entered")
    @Past(message = "birth date must be in the past")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotEmpty(message = "gender must be filled")
    @Pattern(regexp = "^(MALE|FEMALE)$", message = "gender must be either 'Male' or 'Female'")
    private String gender;

    @NotNull(message = "height must be entered")
    @Positive(message = "height must be a positive number")
    @Min(value = 50, message = "height is too low (min 50 cm)")
    @Max(value = 250, message = "height is too high (max 250 cm)")
    private Integer height;

    @NotNull(message = "weight must be entered")
    @Positive(message = "weight must be a positive number")
    @Min(value = 20, message = "weight is too low (min 20 kg)")
    @Max(value = 300, message = "weight is too high (max 300 kg)")
    private Integer weight;

    @NotEmpty
    @Pattern(regexp = "^(beginners|intermediate|advanced)$")
    private String level;

}
