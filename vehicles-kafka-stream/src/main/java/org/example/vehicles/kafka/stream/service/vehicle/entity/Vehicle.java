package org.example.vehicles.kafka.stream.service.vehicle.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.spatial4j.io.jackson.ShapeDeserializer;
import org.locationtech.spatial4j.shape.Point;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Vehicle {
    private UUID id;
    @JsonDeserialize(using = ShapeDeserializer.class)
    private Point location;
}
