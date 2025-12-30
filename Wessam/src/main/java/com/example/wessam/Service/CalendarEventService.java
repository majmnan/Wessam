package com.example.wessam.Service;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Event;
import org.springframework.stereotype.Service;

import com.google.api.services.calendar.Calendar;
import java.util.Date;

@Service
public class CalendarEventService {

    private final GoogleCalendarService googleCalendarService;

    public CalendarEventService(GoogleCalendarService googleCalendarService) {
        this.googleCalendarService = googleCalendarService;
    }

    public void createCourseReminder(String courseName, Date startTime) throws Exception {
        Calendar calendar = googleCalendarService.getCalendarService();

        Event event = new Event()
                .setSummary("Course Reminder: " + courseName)
                .setDescription("Your course will start soon!");

        DateTime startDateTime = new DateTime(startTime);
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("Asia/Riyadh");

        event.setStart(start);
        event.setEnd(start);

        calendar.events()
                .insert("primary", event)
                .execute();
    }
}