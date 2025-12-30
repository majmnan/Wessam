package com.example.wessam.DTO.IN;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDTO {
    private CardDTOIn card;
    private Integer amount;
    private String currency;
    private String description;
    private String callbackUrl;
}
