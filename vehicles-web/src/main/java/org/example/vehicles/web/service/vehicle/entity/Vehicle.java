package org.example.vehicles.web.service.vehicle.entity;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class Vehicle {
    UUID id;
    String latitude;
    String longitude;
}
