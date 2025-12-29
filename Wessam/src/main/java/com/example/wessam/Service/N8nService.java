package com.example.wessam.Service;

import com.example.wessam.DTO.IN.N8nPdfCertGenDTOIn;
import com.example.wessam.DTO.OUT.N8nPdfCertGenDtoOUT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class N8nService {
    private final RestTemplate restTemplate = new RestTemplate();
    public N8nPdfCertGenDtoOUT triggerPdf(N8nPdfCertGenDTOIn dto) {
        String url = "https://d7-ak.app.n8n.cloud/webhook/certificate1";
        return restTemplate.postForObject(url,dto, N8nPdfCertGenDtoOUT.class);
    }
}
