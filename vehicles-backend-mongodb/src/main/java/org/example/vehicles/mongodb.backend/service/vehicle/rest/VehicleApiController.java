package org.example.vehicles.mongodb.backend.service.vehicle.rest;

import org.example.vehicles.mongodb.backend.service.vehicle.repository.VehicleRepository;
import org.example.vehicles.mongodb.backend.service.vehicle.entity.Vehicle;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleApiController {
    private final VehicleRepository repository;

    public VehicleApiController(VehicleRepository repository) {
        this.repository = repository;
    }

    @GetMapping("{id}")
    ResponseEntity<Vehicle> getVehicle(@PathVariable("id") UUID id) {
        return ResponseEntity.of(repository.findById(id));
    }

    @GetMapping("")
    List<Vehicle> getVehicles() {
        return repository.findAll();
    }
}
