package org.example.vehicles.kafka.backend.service.vehicle.rest;

import org.example.vehicles.kafka.backend.service.vehicle.dao.VehicleDao;
import org.example.vehicles.kafka.backend.service.vehicle.entity.Location;
import org.example.vehicles.kafka.backend.service.vehicle.entity.Vehicle;
import org.example.vehicles.kafka.backend.service.vehicle.entity.Vehicles;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class VehicleController {
    private final VehicleDao vehicleDao;

    public VehicleController(VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    @PostMapping("vehicle/{id}")
    public ResponseEntity<?> postPosition(@PathVariable("id") UUID id, @RequestBody Location location) {
        vehicleDao.postPosition(id, location);

        return ResponseEntity.ok().build();
    }

    @GetMapping("vehicles")
    public Vehicles getVehicles(@RequestParam(name = "id") UUID id,
                                @RequestParam(name = "longitude") double longitude,
                                @RequestParam(name = "latitude") double latitude,
                                @RequestParam(name = "radius") double radius) {
        vehicleDao.getVehicles(id, longitude, latitude, radius);

        return Vehicles.builder()
                .build();
    }

    @PostMapping("vehicles")
    public Vehicle registerVehicles() {
        return vehicleDao.registerVehicle();
    }
}
