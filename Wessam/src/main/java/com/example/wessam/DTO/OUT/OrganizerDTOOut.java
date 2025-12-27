package com.example.wessam.DTO.OUT;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrganizerDTOOut {
    private String userName;


    private String name;

    private String businessCertificateId;

    private String status;
}
