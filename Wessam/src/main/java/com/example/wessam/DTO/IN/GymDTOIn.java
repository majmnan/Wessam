package com.example.wessam.DTO.IN;

import com.example.wessam.Model.Branch;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class GymDTOIn {

    @NotEmpty(message = "username must be filled")
    @Size(min = 4, max = 20, message = "username size must be at least 4 maximum 20")
    @Pattern(regexp = "^[A-Za-z0-9]*$", message = "username must contain only letters and numbers")
    private String username;

    @NotEmpty(message = "password must be filled")
    @Size(min = 8, message = "password must be at least 8 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!_]).{8,}$",
            message = "password must contain at least one uppercase, one lowercase, one number and one special character")
    private String password;

    @NotEmpty
    private String name;

    @NotEmpty(message = "business certificate id must be entered")
    @Size(max = 10,message = "business certificate id  must be maximum  size of 10")
    private String businessCertificateId;

    @NotEmpty(message = "gym description must be entered")
    @Size(max = 250,message = "gym description must be maximum  size of 250")
    private String description;
}
