package org.example.vehicles.common.vehicle.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vehicles {
    @Singular
    List<Vehicle> vehicles;
}
