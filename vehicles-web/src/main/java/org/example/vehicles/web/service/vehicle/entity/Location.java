package org.example.vehicles.web.service.vehicle.entity;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Location {
    /**
     * Longitude, in degrees. This value is in the range [-180, 180).
     */
    Double longitude;
    /**
     * Latitude, in degrees. This value is in the range [-90, 90].
     */
    Double latitude;
}
