package com.example.wessam.DTO.IN;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
@AllArgsConstructor
public class BranchDTOIn {
    @NotEmpty
    private String city;

    @URL
    private String Location;
}
