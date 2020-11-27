package org.example.vehicles.common.vehicle.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Vehicle {
    private UUID id;
    private Location location;
    private OffsetDateTime registrationDateTime;
    private OffsetDateTime positionDateTime;
}
