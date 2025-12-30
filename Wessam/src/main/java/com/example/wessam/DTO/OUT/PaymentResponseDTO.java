package com.example.wessam.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class PaymentResponseDTO {

    private String id;
    private String status;
    private double amount;

    private String currency;

    private String description;

    private String amount_format;

    private String callback_url;

    private PaymentResponseSourceDTO source;
}


