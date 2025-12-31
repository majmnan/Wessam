package com.example.wessam.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalenderDTOOut {

    private String courseName;
    private LocalDateTime reminderDate;
}
