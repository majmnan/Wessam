package com.example.wessam.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GymDTOOut {
    private Integer id;
        private String name;
        private String description;
        private LocalDate subscriptionEndDate;
}
