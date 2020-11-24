package org.example.vehicles.web.service.vehicle.dao;

import org.example.vehicles.web.service.vehicle.entity.Location;
import org.example.vehicles.web.service.vehicle.entity.Vehicle;
import org.example.vehicles.web.service.vehicle.entity.Vehicles;

import java.util.UUID;

public interface VehicleDao {
    /**
     * Register a vehicle.
     *
     * @return the id of the registered vehicle
     */
    Vehicle registerVehicle();

    /**
     * Update the position of the vehicle.
     *
     * @param uuid     id of the vehicle
     * @param location the new position of the vehicle
     */
    void postPosition(UUID uuid, Location location);

    /**
     * We want to find places within a distance r=radius from M=(latitude, longitude).
     *
     * @param longitude Longitude, in degrees. This value is in the range [-180, 180).
     * @param latitude  Latitude, in degrees. This value is in the range [-90, 90].
     * @param radius    a distance r=radius from M=(latitude, longitude)
     * @return list of vehicles within the distance from (latitude, longitude)
     */
    Vehicles getVehicleWithinCircle(double longitude, double latitude, double radius);
}
