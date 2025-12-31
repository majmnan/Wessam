package com.example.wessam.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PaymentResponseSourceDTO {
    private String transaction_url;
    private String message;
}
