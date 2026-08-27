package com.example.event_reminder.repository;

import com.example.event_reminder.model.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
}