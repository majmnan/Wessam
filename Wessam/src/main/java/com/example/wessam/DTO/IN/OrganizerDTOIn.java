package com.example.wessam.DTO.IN;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrganizerDTOIn {

    @NotEmpty(message = "organizer name must be entered")
    @Pattern(regexp = "^[a-zA-Z]$",message = "organizer name must be only letters")
    @Size(min = 2,max = 25,message = "organizer name must be at least size of 2 and maximum size of 25")
    private String name;

    @NotEmpty(message = "business certificate id must be entered")
    private String businessCertificateId;

    @Pattern(regexp = "^(inActive|Active)$")
    private String status;

    @NotEmpty(message = "username must be filled")
    @Size(min = 4, max = 20, message = "username size must be at least 4 maximum 20")
    @Pattern(regexp = "^[A-Za-z0-9]*$", message = "username must contain only letters and numbers")
    private String username;

    @NotEmpty(message = "password must be filled")
    @Size(min = 8, message = "password must be at least 8 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$",
            message = "password must contain at least one uppercase, one lowercase, one number and one special character")
    private String password;
}
