package org.example.vehicles.mongodb.backend.service.vehicle.rest;

import org.example.vehicles.mongodb.backend.service.vehicle.dao.VehicleDao;
import org.example.vehicles.mongodb.backend.service.vehicle.dto.NotificationRequest;
import org.example.vehicles.mongodb.backend.service.vehicle.entity.Location;
import org.example.vehicles.mongodb.backend.service.vehicle.entity.Vehicle;
import org.example.vehicles.mongodb.backend.service.vehicle.entity.Vehicles;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class VehicleController {
    private final VehicleDao vehicleDao;

    public VehicleController(VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    @PostMapping("vehicle/{id}")
    ResponseEntity<?> postPosition(@PathVariable("id") UUID id, @RequestBody Location location) {
        final GeoJsonPoint geoJsonPoint = new GeoJsonPoint(location.getLongitude(), location.getLatitude());

        return vehicleDao.postPosition(id, geoJsonPoint)
                .map(vehicle -> ResponseEntity.ok().build())
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("vehicles")
    Vehicles getVehicles(@RequestParam(name = "longitude") double longitude,
                         @RequestParam(name = "latitude") double latitude,
                         @RequestParam(name = "radius") double radius) {
        final List<Vehicle> vehicles = vehicleDao.getVehicles(
                new GeoJsonPoint(longitude, latitude),
                new Distance(radius / 1000, Metrics.KILOMETERS));

        return Vehicles.builder().vehicles(vehicles).build();
    }

    @PostMapping("vehicles")
    Vehicle registerVehicles() {
        return vehicleDao.registerVehicle();
    }

    @PostMapping("notifications")
    ResponseEntity<?> postNotification(@RequestBody NotificationRequest notificationRequest) {
        return vehicleDao.saveNotification(notificationRequest)
                .map(vehicle -> ResponseEntity.ok().build())
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
