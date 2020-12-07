package org.example.vehicles.kafka.stream.service.vehicle;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.example.vehicles.kafka.stream.service.vehicle.entity.Vehicle;
import org.example.vehicles.kafka.stream.service.vehicle.entity.VehicleBeacon;
import org.example.vehicles.kafka.stream.service.vehicle.entity.VehicleBeaconJoin;
import org.example.vehicles.kafka.stream.service.vehicle.entity.Vehicles;
import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.io.GeohashUtils;
import org.locationtech.spatial4j.shape.Point;
import org.locationtech.spatial4j.shape.impl.GeoCircle;
import org.springframework.kafka.config.KafkaStreamsInfrastructureCustomizer;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.List;
import java.util.UUID;

public class VehicleGeoHashCustomizer implements KafkaStreamsInfrastructureCustomizer {
    private final Serde<UUID> uuidSerde;
    private final Serde<String> stringSerde;
    private final JsonSerde<VehicleBeacon> beaconSerde;
    private final JsonSerde<VehicleBeaconJoin> beaconJoinSerde;
    private final Consumed<UUID, VehicleBeacon> beaconConsumed;
    private final Consumed<String, VehicleBeacon> beaconByGeoHashConsumed;
    private final Produced<String, VehicleBeacon> beaconByGeoHashProduced;
    private final Produced<UUID, VehicleBeaconJoin> beaconJoinProduced;
    private final JsonSerde<Vehicle> positionSerde;
    private final JsonSerde<Vehicles> positionsSerde;
    private final Consumed<UUID, Vehicle> positionConsumed;
    private final Consumed<String, Vehicle> positionByGeoHashConsumed;
    private final Produced<String, Vehicle> positionByGeoHashProduced;
    private final Consumed<String, Vehicles> positionsByGeoHashConsumed;
    private final Produced<String, Vehicles> positionsByGeoHashProduced;

    public VehicleGeoHashCustomizer(ObjectMapper objectMapper) {
        this.uuidSerde = Serdes.UUID();
        this.stringSerde = Serdes.String();
        this.beaconSerde = new JsonSerde<>(VehicleBeacon.class, objectMapper).ignoreTypeHeaders();
        this.beaconJoinSerde = new JsonSerde<>(VehicleBeaconJoin.class, objectMapper).ignoreTypeHeaders();
        this.beaconConsumed = Consumed.with(uuidSerde, beaconSerde);
        this.beaconJoinProduced = Produced.with(uuidSerde, beaconJoinSerde);
        this.beaconByGeoHashConsumed = Consumed.with(stringSerde, beaconSerde);
        this.beaconByGeoHashProduced = Produced.with(stringSerde, beaconSerde);
        this.positionSerde = new JsonSerde<>(Vehicle.class, objectMapper).ignoreTypeHeaders();
        this.positionsSerde = new JsonSerde<>(Vehicles.class, objectMapper).ignoreTypeHeaders();
        this.positionConsumed = Consumed.with(uuidSerde, positionSerde);
        this.positionByGeoHashConsumed = Consumed.with(stringSerde, positionSerde);
        this.positionByGeoHashProduced = Produced.with(stringSerde, positionSerde);
        this.positionsByGeoHashConsumed = Consumed.with(stringSerde, positionsSerde);
        this.positionsByGeoHashProduced = Produced.with(stringSerde, positionsSerde);
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

        builder.stream("vehicle.position-by-geo-hash", positionByGeoHashConsumed)
                .groupByKey()
                // .windowedBy(TimeWindows.of(Duration.ofSeconds(5)).advanceBy(Duration.ofSeconds(1)))
                .aggregate(Vehicles::new,
                        (key, value, aggregate) -> {
                            aggregate.getVehicles().put(value.getId(), value);
                            return aggregate;
                        },
                        Materialized.with(stringSerde, positionsSerde))
                .toStream()
                .to("vehicle.position-by-geo-hash.aggregate", positionsByGeoHashProduced);

        final KStream<String, Vehicles> vehiclesStream = builder.stream("vehicle.position-by-geo-hash.aggregate", positionsByGeoHashConsumed);
        builder.stream("vehicle.beacon-by-geo-hash", beaconByGeoHashConsumed)
                .leftJoin(vehiclesStream.toTable(),
                        (beacon, vehicles) -> {
                            final VehicleBeaconJoin.VehicleBeaconJoinBuilder joinBuilder = VehicleBeaconJoin.builder().id(beacon.getId());
                            if (vehicles == null) {
                                return joinBuilder.build();
                            }

                            final GeoCircle circle = beacon.getCircle();
                            return vehicles.getVehicles().values().stream()
                                    .filter(vehicle -> !beacon.getId().equals(vehicle.getId()))
                                    .filter(vehicle ->
                                            SpatialContext.GEO.calcDistance(circle.getCenter(), vehicle.getLocation()) <= circle.getRadius())
                                    .collect(() -> joinBuilder,
                                            VehicleBeaconJoin.VehicleBeaconJoinBuilder::vehicle,
                                            (vehicleBeaconJoinBuilder, vehicleBeaconJoinBuilder2) -> {
                                                final List<Vehicle> vehicleList = vehicleBeaconJoinBuilder2.build().getVehicles();
                                                vehicleList.forEach(vehicleBeaconJoinBuilder::vehicle);
                                            })
                                    .build();

                        }
                        // JoinWindows.of(Duration.ofSeconds(5L)),
                        // StreamJoined.with(uuidSerde, beaconSerde, positionsSerde)
                )
                .selectKey((key, value) -> value.getId())
                .to("vehicle.beacon-vehicles", beaconJoinProduced);
    }
}
