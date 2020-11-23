package org.example.vehicles.web.service.vehicle.entity;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Vehicles {
    @Singular
    List<Vehicle> vehicles;
}
