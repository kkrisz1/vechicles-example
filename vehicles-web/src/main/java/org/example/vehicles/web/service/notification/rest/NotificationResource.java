package org.example.vehicles.web.service.notification.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.vehicles.web.service.notification.entity.Notification;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Tag(name = "notification")
@Path("")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface NotificationResource {
    @POST
    @Path("notifications")
    Response postNotification(Notification notification);
}
