package org.example.vehicles.kafka.stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.vehicles.kafka.stream.service.vehicle.VehicleGeoHashCustomizer;
import org.locationtech.spatial4j.io.jackson.ShapesAsGeoJSONModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.config.KafkaStreamsInfrastructureCustomizer;
import org.springframework.kafka.config.StreamsBuilderFactoryBeanCustomizer;
import org.springframework.stereotype.Component;

@Component
@EnableKafkaStreams
public class KafkaConfiguration {
    @Bean
    public StreamsBuilderFactoryBeanCustomizer myStreamsBuilderFactoryBeanCustomizer(KafkaStreamsInfrastructureCustomizer infrastructureCustomizer) {
        return factoryBean -> factoryBean.setInfrastructureCustomizer(infrastructureCustomizer);
    }

    @Bean
    public KafkaStreamsInfrastructureCustomizer myKafkaStreamsInfrastructureCustomizer(ObjectMapper objectMapper) {
        return new VehicleGeoHashCustomizer(objectMapper);
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer spatialCustomizer() {
        return builder -> builder.modules(new ShapesAsGeoJSONModule());
    }
}
