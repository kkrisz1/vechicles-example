package org.example.vehicles.web.service.vehicle.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.ws.rs.HeaderParam;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleParam {
    @HeaderParam("latitude")
    String latitude;
    @HeaderParam("longitude")
    String longitude;
    @HeaderParam("radius")
    float radius;
}
