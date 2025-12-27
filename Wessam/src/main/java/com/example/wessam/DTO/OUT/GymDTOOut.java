package com.example.wessam.DTO.OUT;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GymDTOOut {
    private String userName;


    private String businuissCertificateId;

    private String status;

    private String description;
}
