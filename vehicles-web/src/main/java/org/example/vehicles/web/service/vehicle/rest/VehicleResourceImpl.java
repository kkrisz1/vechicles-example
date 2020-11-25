package org.example.vehicles.web.service.vehicle.rest;

import org.example.vehicles.common.vehicle.entity.Location;
import org.example.vehicles.common.vehicle.entity.Vehicle;
import org.example.vehicles.common.vehicle.entity.VehicleBeacon;
import org.example.vehicles.common.vehicle.entity.Vehicles;
import org.example.vehicles.web.service.vehicle.dao.VehicleDao;
import org.springframework.stereotype.Service;

import java.util.UUID;
import javax.ws.rs.core.Response;

@Service
public class VehicleResourceImpl implements VehicleResource {
    private final VehicleDao vehicleDao;

    public VehicleResourceImpl(VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    @Override
    public Response postPosition(UUID id, Location location) {
        vehicleDao.postPosition(id, location);

        return Response.ok().build();
    }

    @Override
    public Vehicle registerVehicles() {
        return vehicleDao.registerVehicle();
    }

    @Override
    public Vehicles getVehicles(VehicleParam param) {
        return vehicleDao.getVehicleWithinCircle(VehicleBeacon.builder()
                .vehicle(Vehicle.builder()
                        .id(param.getId())
                        .location(Location.builder()
                                .latitude(param.getLatitude())
                                .longitude(param.getLongitude())
                                .build())
                        .build())
                .radius(param.getRadius())
                .build()
        );
    }
}
