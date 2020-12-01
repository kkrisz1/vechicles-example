package org.example.vehicles.kafka.stream.service.vehicle;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;
import org.example.vehicles.kafka.stream.service.vehicle.entity.Vehicle;
import org.example.vehicles.kafka.stream.service.vehicle.entity.VehicleBeacon;
import org.locationtech.spatial4j.io.GeohashUtils;
import org.locationtech.spatial4j.shape.Point;
import org.springframework.kafka.config.KafkaStreamsInfrastructureCustomizer;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.UUID;

public class VehicleGeoHashCustomizer implements KafkaStreamsInfrastructureCustomizer {
    private final Serde<UUID> uuidSerde;
    private final Serde<String> stringSerde;
    private final JsonSerde<VehicleBeacon> beaconSerde;
    private final Consumed<UUID, VehicleBeacon> beaconConsumed;
    private final Produced<String, VehicleBeacon> beaconByGeoHashProduced;
    private final JsonSerde<Vehicle> positionSerde;
    private final Consumed<UUID, Vehicle> positionConsumed;
    private final Produced<String, Vehicle> positionByGeoHashProduced;

    public VehicleGeoHashCustomizer(ObjectMapper objectMapper) {
        this.uuidSerde = Serdes.UUID();
        this.stringSerde = Serdes.String();
        this.beaconSerde = new JsonSerde<>(VehicleBeacon.class, objectMapper).ignoreTypeHeaders();
        this.beaconConsumed = Consumed.with(uuidSerde, beaconSerde);
        this.beaconByGeoHashProduced = Produced.with(stringSerde, beaconSerde);
        this.positionSerde = new JsonSerde<>(Vehicle.class, objectMapper).ignoreTypeHeaders();
        this.positionConsumed = Consumed.with(uuidSerde, positionSerde);
        this.positionByGeoHashProduced = Produced.with(stringSerde, positionSerde);
    }

    @Override
    public void configureBuilder(StreamsBuilder builder) {
        builder.stream("vehicle.beacon", beaconConsumed)
                .selectKey((key, value) -> {
                    final Point point = value.getCircle().getCenter();
                    return GeohashUtils.encodeLatLon(point.getLat(), point.getLon(), 5);
                })
                .to("vehicle.beacon-by-geo-hash", beaconByGeoHashProduced);

        builder.stream("vehicle.position", positionConsumed)
                .selectKey((key, value) -> {
                    final Point point = value.getLocation();
                    return GeohashUtils.encodeLatLon(point.getLat(), point.getLon(), 5);
                })
                .to("vehicle.position-by-geo-hash", positionByGeoHashProduced);
    }
}
