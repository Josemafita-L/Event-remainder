package com.example.event_reminder.controller;

import com.example.event_reminder.model.Reminder;
import com.example.event_reminder.repository.ReminderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reminders")
public class ReminderController {

    private final ReminderRepository reminderRepository;

    public ReminderController(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    // POST - Create Reminder
    @PostMapping("/create")
    public ResponseEntity<Reminder> createReminder(
            @RequestBody Reminder reminder) {

        Reminder savedReminder = reminderRepository.save(reminder);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedReminder);
    }

    // GET - Get Reminder Details
    @GetMapping("/details")
    public ResponseEntity<List<Reminder>> getReminderDetails() {

        List<Reminder> reminders = reminderRepository.findAll();

        return ResponseEntity.ok(reminders);
    }
}