package com.example.wessam.Service;

import com.example.wessam.DTO.IN.PaymentRequestDTO;
import com.example.wessam.DTO.OUT.PaymentResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PaymentService {
    @Value("${moyasar.api.key}")
    private String apiKey;

    private static final String MOYASAR_API_URL = "https://api.moyasar.com/v1/payments/";

    RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<PaymentResponseDTO> processPayment(PaymentRequestDTO dto){

        String requestBody = String.format(
                "source[type]=card&source[name]=%s&source[number]=%s&source[cvc]=%s&"+
                        "source[month]=%s&source[year]=%s&amount=%d&currency=%s&callback_url=%s",
                dto.getCard().getName(),
                dto.getCard().getNumber(),
                dto.getCard().getCvc(),
                dto.getCard().getMonth(),
                dto.getCard().getYear(),
                dto.getAmount()*100,
                dto.getCurrency(),
                dto.getCallbackUrl()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(apiKey, "");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<PaymentResponseDTO> response = restTemplate.exchange(MOYASAR_API_URL, HttpMethod.POST, entity, PaymentResponseDTO.class);

        return response;
    }

    public ResponseEntity<PaymentResponseDTO>  getPayment(String paymentId){
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(apiKey, "");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<PaymentResponseDTO> response = restTemplate.exchange(
            MOYASAR_API_URL + paymentId, HttpMethod.GET, entity, PaymentResponseDTO.class
        );

        return response;
    }
}
