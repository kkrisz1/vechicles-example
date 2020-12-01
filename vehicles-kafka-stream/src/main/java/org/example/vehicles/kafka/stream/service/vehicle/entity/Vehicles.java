package org.example.vehicles.kafka.stream.service.vehicle.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vehicles {
    @Singular
    private List<Vehicle> vehicles = new ArrayList<>();
}
