package org.example.vehicles.web.service.vehicle.rest;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.vehicles.common.vehicle.entity.Location;
import org.example.vehicles.common.vehicle.entity.Vehicle;
import org.example.vehicles.common.vehicle.entity.Vehicles;

import java.util.UUID;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Tag(name = "vehicle")
@Path("")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface VehicleResource {
    @POST
    @Path("vehicle/{id}")
    Response postPosition(@Parameter(description = "UUID of the vehicle")
                          @PathParam("id") UUID id,
                          Location location);

    @GET
    @Path("vehicles")
    Vehicles getVehicles(@BeanParam VehicleParam param);

    @POST
    @Path("vehicles")
    Vehicle registerVehicles();
}
