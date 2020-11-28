package org.example.vehicles.mongodb.backend.service.vehicle.rest;

import org.example.vehicles.mongodb.backend.service.vehicle.entity.Vehicle;
import org.example.vehicles.mongodb.backend.service.vehicle.repository.VehicleRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleApiController {
    private final VehicleRepository repository;

    public VehicleApiController(VehicleRepository repository) {
        this.repository = repository;
    }

    @GetMapping("{id}")
    Mono<Vehicle> getVehicle(@PathVariable("id") UUID id) {
        return repository.findById(id);
    }

    @GetMapping("")
    Flux<Vehicle> getVehicles() {
        return repository.findAll();
    }
}
