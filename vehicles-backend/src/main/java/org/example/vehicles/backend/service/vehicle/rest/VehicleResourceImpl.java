package org.example.vehicles.backend.service.vehicle.rest;

import org.example.vehicles.common.vehicle.entity.Location;
import org.example.vehicles.common.vehicle.entity.Vehicle;
import org.example.vehicles.common.vehicle.entity.VehicleBeacon;
import org.example.vehicles.common.vehicle.entity.Vehicles;
import org.example.vehicles.backend.service.vehicle.dao.VehicleDao;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class VehicleResourceImpl implements VehicleResource {
    public static final String MISSING_QUERY_PARAM = "Missing query param: '%s'";
    public static final String MISSING_INFO = "Missing info: '%s'";

    private final VehicleDao vehicleDao;

    public VehicleResourceImpl(VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    @Override
    public Response postPosition(UUID id, Location location) {
        checkNullForParam(id, String.format(MISSING_INFO, "id"));
        checkNullForParam(location, String.format(MISSING_INFO, "location"));
        checkNullForParam(location.getLatitude(), String.format(MISSING_INFO, "latitude"));
        checkNullForParam(location.getLongitude(), String.format(MISSING_INFO, "longitude"));

        vehicleDao.postPosition(id, location);

        return Response.ok().build();
    }

    @Override
    public Vehicle registerVehicles() {
        return vehicleDao.registerVehicle();
    }

    @Override
    public Vehicles getVehicles(VehicleParam param) {
        checkNullForParam(param.getId(), String.format(MISSING_QUERY_PARAM, "id"));
        checkNullForParam(param.getLatitude(), String.format(MISSING_QUERY_PARAM, "latitude"));
        checkNullForParam(param.getLongitude(), String.format(MISSING_QUERY_PARAM, "longitude"));
        checkNullForParam(param.getRadius(), String.format(MISSING_QUERY_PARAM, "radius"));

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

    private <T> void checkNullForParam(T param, String msg) {
        checkForParam(param, Objects::isNull, msg);
    }

    private <T> void checkForParam(T param, Predicate<T> checker, String msg) {
        if (checker.test(param)) {
            throw new BadRequestException(msg, Response
                    .status(Response.Status.BAD_REQUEST)
                    .type(MediaType.TEXT_PLAIN_TYPE)
                    .entity(msg)
                    .build());
        }
    }
}
