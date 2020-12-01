package org.example.vehicles.kafka.backend.service.notification.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class Notification {
    @JsonProperty("vehicle_id")
    private UUID vehicleId;
    private String message;
}
