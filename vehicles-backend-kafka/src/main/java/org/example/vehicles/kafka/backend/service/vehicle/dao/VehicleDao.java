package org.example.vehicles.kafka.backend.service.vehicle.dao;

import org.example.vehicles.kafka.backend.service.vehicle.entity.Location;
import org.example.vehicles.kafka.backend.service.vehicle.entity.Vehicle;
import org.example.vehicles.kafka.backend.service.vehicle.entity.VehicleBeacon;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VehicleDao {
    private final KafkaTemplate<Object, Object> template;

    public VehicleDao(KafkaTemplate<?, ?> template) {
        this.template = (KafkaTemplate<Object, Object>) template;
    }

    public Vehicle registerVehicle() {
        return Vehicle.builder()
                .id(UUID.randomUUID())
                .build();
    }

    public void postPosition(UUID id, Location point) {
        final Vehicle vehicle = Vehicle.builder()
                .id(id)
                .location(point)
                .build();

        template.send("vehicle.position", id, vehicle);
    }

    public void getVehicles(VehicleBeacon beacon) {
        template.send("vehicle.beacon", beacon.getId(), beacon);
    }
}
