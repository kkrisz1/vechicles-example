package org.example.vehicles.backend.service.vehicle.dao;

import org.example.vehicles.common.vehicle.entity.Location;
import org.example.vehicles.common.vehicle.entity.Vehicle;
import org.example.vehicles.common.vehicle.entity.VehicleBeacon;
import org.example.vehicles.common.vehicle.entity.Vehicles;

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
     * @param beacon   vehicle beacon
     * @return list of vehicles within the distance from (latitude, longitude)
     */
    Vehicles getVehicleWithinCircle(VehicleBeacon beacon);
}
