package org.example.vehicles.web.service.notification.rest;

import org.example.vehicles.web.service.notification.entity.Notification;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;

@Service
public class NotificationResourceImpl implements NotificationResource {
    @Override
    public Response postNotification(Notification notification) {
        return Response.ok().build();
    }
}
