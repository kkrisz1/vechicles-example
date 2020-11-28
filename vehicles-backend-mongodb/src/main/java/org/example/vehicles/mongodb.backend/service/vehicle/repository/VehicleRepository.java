package org.example.vehicles.mongodb.backend.service.vehicle.repository;

import org.example.vehicles.mongodb.backend.service.vehicle.entity.Vehicle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface VehicleRepository extends ReactiveMongoRepository<Vehicle, UUID> {
    Flux<Vehicle> findAllByLocationNear(Point point, Distance distance);
}
