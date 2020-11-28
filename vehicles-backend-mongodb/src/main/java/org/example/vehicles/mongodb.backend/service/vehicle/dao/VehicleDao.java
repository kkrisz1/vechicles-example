package org.example.vehicles.mongodb.backend.service.vehicle.dao;

import org.example.vehicles.mongodb.backend.service.vehicle.dto.NotificationRequest;
import org.example.vehicles.mongodb.backend.service.vehicle.entity.Vehicle;
import org.example.vehicles.mongodb.backend.service.vehicle.repository.VehicleRepository;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class VehicleDao {
    private final VehicleRepository repository;

    public VehicleDao(VehicleRepository repository) {
        this.repository = repository;
    }

    public Mono<Vehicle> registerVehicle() {
        final Vehicle vehicle = Vehicle.builder()
                .id(UUID.randomUUID())
                .build();

        return repository.save(vehicle);
    }

    public Mono<Vehicle> postPosition(UUID id, GeoJsonPoint point) {
        return repository.findById(id)
                .flatMap(vehicle -> {
                    vehicle.setLocation(point);
                    return repository.save(vehicle);
                });
    }

    public Flux<Vehicle> getVehicles(Point point, Distance distance) {
        return repository.findAllByLocationNear(point, distance);
    }

    public Mono<Vehicle> saveNotification(NotificationRequest notificationRequest) {
        return repository.findById(notificationRequest.getVehicleId())
                .flatMap(vehicle -> {
                    vehicle.setNotification(notificationRequest.getMessage());
                    return repository.save(vehicle);
                });
    }
}
