package org.example.vehicles.kafka.backend.service.vehicle.dao;

import org.example.vehicles.kafka.backend.service.vehicle.entity.Location;
import org.example.vehicles.kafka.backend.service.vehicle.entity.Vehicle;
import org.example.vehicles.kafka.backend.service.vehicle.entity.VehicleBeacon;
import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.distance.DistanceUtils;
import org.locationtech.spatial4j.shape.ShapeFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VehicleDao {
    private final KafkaTemplate<Object, Object> template;
    private final ShapeFactory shapeFactory = SpatialContext.GEO.getShapeFactory();

    public VehicleDao(KafkaTemplate<?, ?> template) {
        this.template = (KafkaTemplate<Object, Object>) template;
    }

    public Vehicle registerVehicle() {
        return Vehicle.builder()
                .id(UUID.randomUUID())
                .build();
    }

    public void postPosition(UUID id, Location point) {
        final Vehicle vehicle = Vehicle.builder()
                .id(id)
                .location(shapeFactory.pointLatLon(point.getLatitude(), point.getLongitude()))
                .build();

        template.send("vehicle.position", id, vehicle);
    }

    public void getVehicles(UUID id, double longitude, double latitude, double radius) {
        final VehicleBeacon beacon = VehicleBeacon.builder()
                .id(id)
                .circle(shapeFactory.circle(longitude, latitude, DistanceUtils.dist2Degrees(radius / 1000, DistanceUtils.EARTH_MEAN_RADIUS_KM)))
                .build();

        template.send("vehicle.beacon", beacon.getId(), beacon);
    }
}
