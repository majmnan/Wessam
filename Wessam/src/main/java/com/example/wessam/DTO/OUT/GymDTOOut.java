package com.example.wessam.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class GymDTOOut {
        private String name;
        private String description;
        private LocalDate subscriptionEndDate;
}
