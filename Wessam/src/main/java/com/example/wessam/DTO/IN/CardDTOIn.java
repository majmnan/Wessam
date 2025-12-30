package com.example.wessam.DTO.IN;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CardDTOIn {
    @NotNull
    private String name;
    @NotNull
    @Size(min = 16, max = 16)
    private String number;
    @NotNull
    @Pattern(regexp = "^\\d{3}$")
    private String cvc;
    @Min(1)
    @Max(12)
    @NotNull
    private String month;
    @NotNull
    private String year;

}
