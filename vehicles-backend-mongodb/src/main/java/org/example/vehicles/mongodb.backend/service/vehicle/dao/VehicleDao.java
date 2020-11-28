package org.example.vehicles.mongodb.backend.service.vehicle.dao;

import org.example.vehicles.mongodb.backend.service.vehicle.dto.NotificationRequest;
import org.example.vehicles.mongodb.backend.service.vehicle.entity.Vehicle;
import org.example.vehicles.mongodb.backend.service.vehicle.repository.VehicleRepository;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VehicleDao {
    private final VehicleRepository repository;

    public VehicleDao(VehicleRepository repository) {
        this.repository = repository;
    }

    public Vehicle registerVehicle() {
        final Vehicle vehicle = Vehicle.builder()
                .id(UUID.randomUUID())
                .build();

        return repository.save(vehicle);
    }

    public Optional<Vehicle> postPosition(UUID id, GeoJsonPoint point) {
        final Optional<Vehicle> searchedVehicle = repository.findById(id);

        if (searchedVehicle.isPresent()) {
            final Vehicle vehicle = searchedVehicle.get();
            vehicle.setLocation(point);

            return Optional.of(repository.save(vehicle));
        }

        return Optional.empty();
    }

    public List<Vehicle> getVehicles(Point point, Distance distance) {
        return repository.findAllByLocationNear(point, distance);
    }

    public Optional<Vehicle> saveNotification(NotificationRequest notificationRequest) {
        final Optional<Vehicle> searchedVehicle = repository.findById(notificationRequest.getVehicleId());
        if (searchedVehicle.isPresent()) {
            final Vehicle vehicle = searchedVehicle.get();
            vehicle.setNotification(notificationRequest.getMessage());

            return Optional.of(repository.save(vehicle));
        }

        return Optional.empty();
    }
}
