package org.example.vehicles.kafka.backend.service.notification.dao;

import org.example.vehicles.kafka.backend.service.notification.entity.Notification;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationDao {
    private final KafkaTemplate<Object, Object> template;

    public NotificationDao(KafkaTemplate<?, ?> template) {
        this.template = (KafkaTemplate<Object, Object>) template;
    }

    public void saveNotification(Notification notification) {
        template.send("vehicle.notification", notification.getMessage(), notification);
    }
}
