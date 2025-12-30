package com.example.wessam.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import com.google.api.services.calendar.Calendar;
import java.util.Collections;

@Service
public class GoogleCalendarService {

    @Value("${google.service.account.key}")
    private String serviceAccountKeyPath;


    public Calendar getCalendarService() throws Exception {
        InputStream in = getClass().getResourceAsStream("/google-service-account.json");

        GoogleCredentials credentials = GoogleCredentials.fromStream(in)
                .createScoped(Collections.singleton(CalendarScopes.CALENDAR));

        return new Calendar.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(credentials)
        ).setApplicationName("Wessam Calendar")
                .build();
    }
}
