package org.example.vehicles.backend.service.vehicle.dao;

import org.assertj.core.api.Assertions;
import org.awaitility.Awaitility;
import org.example.vehicles.common.vehicle.entity.Location;
import org.example.vehicles.common.vehicle.entity.Vehicle;
import org.example.vehicles.common.vehicle.entity.VehicleBeacon;
import org.example.vehicles.common.vehicle.entity.Vehicles;
import org.junit.Test;

import java.time.Duration;
import java.util.UUID;

public class VehicleDaoTest {
    private final VehicleDao vehicleDao = new VehicleDaoImpl(Duration.ofMillis(100L));

    @Test
    public void getVehicleWithinCircle_noVehicleAround() {
        // given
        final Vehicle registerVehicle = vehicleDao.registerVehicle();
        final Vehicle expected = createDefaultVehicle(registerVehicle.getId());
        vehicleDao.postPosition(expected.getId(), expected.getLocation());

        // when
        final VehicleBeacon beacon = VehicleBeacon.builder()
                .vehicle(expected)
                .radius(1000)
                .build();
        final Vehicles vehicles = vehicleDao.getVehicleWithinCircle(beacon);

        // then
        Assertions
                .assertThat(vehicles)
                .isNotNull();
        Assertions
                .assertThat(vehicles.getVehicles())
                .isEmpty();
    }

    @Test
    public void getVehicleWithinCircle_oneVehicleAround() {
        // given
        final Vehicle registerVehicle = vehicleDao.registerVehicle();
        final Vehicle expected = createDefaultVehicle(registerVehicle.getId());
        vehicleDao.postPosition(expected.getId(), expected.getLocation());

        // when
        final VehicleBeacon beacon = VehicleBeacon.builder()
                .vehicle(createDefaultVehicle(UUID.randomUUID()))
                .radius(1000)
                .build();
        final Vehicles vehicles = vehicleDao.getVehicleWithinCircle(beacon);

        // then
        Assertions
                .assertThat(vehicles)
                .isNotNull();
        Assertions
                .assertThat(vehicles.getVehicles())
                .isNotEmpty()
                .hasSize(1)
                .hasOnlyOneElementSatisfying(vehicle ->
                        Assertions.assertThat(vehicle.getId()).isEqualTo(expected.getId()));
    }

    @Test
    public void getVehicleWithinCircle_oldVehiclePosition_noVehicleAround() {
        // given
        final Vehicle registerVehicle = vehicleDao.registerVehicle();
        final UUID id = registerVehicle.getId();
        final Vehicle expected = createDefaultVehicle(id);
        vehicleDao.postPosition(expected.getId(), expected.getLocation());

        // when
        Awaitility.await()
                .atLeast(Duration.ofMillis(150L))
                .until(() -> {
                    Thread.sleep(100L);
                    return true;
                });
        final VehicleBeacon beacon = VehicleBeacon.builder()
                .vehicle(createDefaultVehicle(UUID.randomUUID()))
                .radius(1000)
                .build();
        final Vehicles vehicles = vehicleDao.getVehicleWithinCircle(beacon);

        // then
        Assertions
                .assertThat(vehicles)
                .isNotNull();
        Assertions
                .assertThat(vehicles.getVehicles())
                .isEmpty();
    }

    private Vehicle createDefaultVehicle(UUID id) {
        return Vehicle.builder()
                .id(id)
                .location(Location.builder()
                        .longitude(15d)
                        .latitude(45d)
                        .build())
                .build();
    }
}
