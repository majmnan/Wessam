package com.example.wessam.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CalenderDTOOut {

    private String courseName;
    private LocalDateTime reminderDate;
}
