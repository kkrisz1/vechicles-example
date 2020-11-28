package org.example.vehicles.mongodb.backend.service.vehicle.dao;

import org.example.vehicles.mongodb.backend.service.vehicle.dto.NotificationRequest;
import org.example.vehicles.mongodb.backend.service.vehicle.entity.Vehicle;
import org.example.vehicles.mongodb.backend.service.vehicle.repository.VehicleRepository;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.ChangeStreamEvent;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.function.Function;

@Service
public class VehicleDao {
    private final VehicleRepository repository;
    private final ReactiveMongoTemplate template;

    public VehicleDao(VehicleRepository repository, ReactiveMongoTemplate template) {
        this.repository = repository;
        this.template = template;
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

    public Flux<Vehicle> subscribe(UUID id) {
        return template.changeStream(Vehicle.class)
                .watchCollection("vehicle")
                .filter(Criteria.where("id").is(id))
                .listen()
                .map(ChangeStreamEvent::getBody);
    }

    public Flux<Vehicle> subscribe() {
        return template.changeStream(Vehicle.class)
                .watchCollection("vehicle")
                .listen()
                .map(ChangeStreamEvent::getBody);
    }

    public <T> T execute(Function<VehicleRepository, T> function) {
        return function.apply(repository);
    }
}
