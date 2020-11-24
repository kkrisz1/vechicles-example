package org.example.vehicles.web.service.vehicle.dao;

import org.assertj.core.api.Assertions;
import org.example.vehicles.web.service.vehicle.entity.Location;
import org.example.vehicles.web.service.vehicle.entity.Vehicle;
import org.example.vehicles.web.service.vehicle.entity.Vehicles;
import org.junit.Test;

public class VehicleDaoTest {
    private final VehicleDao vehicleDao = new VehicleDaoImpl();

    @Test
    public void getVehicleWithinCircle() {
        // given
        final Vehicle actual = vehicleDao.registerVehicle();
        final Vehicle expected = Vehicle.builder().id(actual.getId()).location(Location.builder().longitude(15d).latitude(45d).build()).build();
        vehicleDao.postPosition(actual.getId(), expected.getLocation());

        // when
        final Vehicles vehicles = vehicleDao.getVehicleWithinCircle(15, 45, 1000);

        // then
        Assertions
                .assertThat(vehicles)
                .isNotNull();
        Assertions
                .assertThat(vehicles.getVehicles())
                .isNotEmpty()
                .hasSize(1)
                .containsExactly(expected);
    }
}
