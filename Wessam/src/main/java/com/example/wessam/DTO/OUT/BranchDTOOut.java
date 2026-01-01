package com.example.wessam.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchDTOOut {
    private Integer id;
    private String name;
    private String description;
    private String city;
    private String Location;
}
