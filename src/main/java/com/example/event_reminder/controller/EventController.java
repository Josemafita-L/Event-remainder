package com.example.event_reminder.controller;

import com.example.event_reminder.model.Event;
import com.example.event_reminder.service.EventService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // ==========================================
    // POST - CREATE EVENT
    // POST /api/events/create
    // ==========================================

    @PostMapping("/create")
    public ResponseEntity<Event> createEvent(
            @RequestBody Event event) {

        Event createdEvent =
                eventService.createEvent(event);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdEvent);
    }


    // ==========================================
    // GET - GET ALL EVENTS
    // GET /api/events
    // ==========================================

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {

        List<Event> events =
                eventService.getAllEvents();

        return ResponseEntity.ok(events);
    }
}