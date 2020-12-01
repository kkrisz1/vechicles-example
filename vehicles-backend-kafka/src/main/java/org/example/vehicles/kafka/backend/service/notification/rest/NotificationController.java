package org.example.vehicles.kafka.backend.service.notification.rest;

import org.example.vehicles.kafka.backend.service.notification.dao.NotificationDao;
import org.example.vehicles.kafka.backend.service.notification.entity.Notification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {
    private final NotificationDao notificationDao;

    public NotificationController(NotificationDao notificationDao) {
        this.notificationDao = notificationDao;
    }

    @PostMapping("notifications")
    public ResponseEntity<?> postNotification(@RequestBody Notification notification) {
        notificationDao.saveNotification(notification);

        return ResponseEntity.ok().build();
    }
}
