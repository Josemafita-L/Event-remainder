package com.example.event_reminder.service;

import com.example.event_reminder.model.Event;
import com.example.event_reminder.repository.EventRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // CREATE EVENT
    public Event createEvent(Event event) {

        // New event is pending by default
        if (event.getStatus() == null ||
                event.getStatus().isEmpty()) {

            event.setStatus("PENDING");
        }

        return eventRepository.save(event);
    }

    // GET ALL EVENTS
    public List<Event> getAllEvents() {

        return eventRepository.findAll();
    }
}