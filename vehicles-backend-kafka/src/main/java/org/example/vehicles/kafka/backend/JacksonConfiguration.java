package org.example.vehicles.kafka.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.locationtech.spatial4j.io.jackson.ShapesAsGeoJSONModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaProducerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class JacksonConfiguration {
    @Bean
    public DefaultKafkaProducerFactoryCustomizer producerFactoryCustomizer(ObjectMapper objectMapper) {
        return producerFactory -> producerFactory.setValueSerializer(new JsonSerializer<>(objectMapper));
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer spatialCustomizer() {
        return builder -> builder.modules(new ShapesAsGeoJSONModule());
    }
}
