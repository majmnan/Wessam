package com.example.wessam.Controller;

import com.example.wessam.Api.ApiResponse;
import com.example.wessam.DTO.IN.CardDTOIn;
import com.example.wessam.DTO.IN.PaymentRequestDTO;
import com.example.wessam.DTO.OUT.PaymentResponseDTO;
import com.example.wessam.Service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentTestController {
    private final PaymentService paymentService;

    @PostMapping("/pay")
    public ResponseEntity<?> payTest(@RequestBody@Valid CardDTOIn card){
        ResponseEntity<PaymentResponseDTO> response = paymentService.processPayment(new PaymentRequestDTO(card, 123, "SAR", "Pay " + "test pay" + "course", "http://localhost:8080/api/v1/payment/callback/Khaled/13"));
        if(!response.getStatusCode().is2xxSuccessful())
            throw new ResponseStatusException(response.getStatusCode(),response.hasBody() ? response.getBody().toString() : "");

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @GetMapping("/callback/{n}/{nn}")
    public ResponseEntity<?> callback(@PathVariable String n, @PathVariable Integer nn, @RequestParam("id") String id){
        ResponseEntity<PaymentResponseDTO> response = paymentService.getPayment(id);
        if("paid".equals(response.getBody().getStatus())){
            //after pay logic
            //ex. change course registration status to paid
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(n+nn));
        }
        return response;
    }
}
