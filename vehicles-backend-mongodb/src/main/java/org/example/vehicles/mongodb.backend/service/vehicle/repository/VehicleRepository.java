package org.example.vehicles.mongodb.backend.service.vehicle.repository;

import org.example.vehicles.mongodb.backend.service.vehicle.entity.Vehicle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface VehicleRepository extends MongoRepository<Vehicle, UUID> {
    List<Vehicle> findAllByLocationNear(Point point, Distance distance);
}
