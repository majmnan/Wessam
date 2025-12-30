package com.example.wessam.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentResponseSourceDTO {
    private String transaction_url;
    private String message;
}
