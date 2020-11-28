package org.example.vehicles.mongodb.backend.service.vehicle.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import org.example.vehicles.mongodb.backend.service.vehicle.entity.Notification;

import java.util.UUID;

@Data
public class NotificationRequest {
    @JsonProperty("vehicle_id")
    UUID vehicleId;
    @JsonUnwrapped
    Notification message;
}
