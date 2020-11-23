package org.example.vehicles.web.service.vehicle.rest;

import org.example.vehicles.web.service.vehicle.entity.Vehicle;
import org.example.vehicles.web.service.vehicle.entity.Vehicles;
import org.springframework.stereotype.Service;

import java.util.UUID;
import javax.ws.rs.core.Response;

@Service
public class VehicleResourceImpl implements VehicleResource {
    @Override
    public Response postPosition(String id) {
        return Response.ok().build();
    }

    @Override
    public Vehicle registerVehicles() {
        return Vehicle.builder()
                .id(UUID.randomUUID())
                .build();
    }

    @Override
    public Vehicles getVehicles(VehicleParam param) {
        return Vehicles.builder()
                .vehicle(Vehicle.builder().id(UUID.randomUUID()).build())
                .build();
    }
}
