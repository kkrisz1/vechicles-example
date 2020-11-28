package org.example.vehicles.mongodb.backend.service.vehicle.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
