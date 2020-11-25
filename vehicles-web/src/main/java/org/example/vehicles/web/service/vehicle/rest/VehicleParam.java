package org.example.vehicles.web.service.vehicle.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
import javax.ws.rs.QueryParam;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleParam {
    @QueryParam("id")
    private UUID id;
    @QueryParam("latitude")
    private double latitude;
    @QueryParam("longitude")
    private double longitude;
    @QueryParam("radius")
    private double radius;
}
