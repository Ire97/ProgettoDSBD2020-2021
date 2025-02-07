package com.baldacchino_gambadoro.fake_producer.Kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaFakeProducerConfiguration {

        @Value("kafka:9092")
        private String bootstrapServers;

        // Bean di creazione di una mappa con i parametri di configurazione per il client kafka
        @Bean
        public Map<String, Object> producerConfigs() {
            Map<String, Object> props = new HashMap<>();
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                    bootstrapServers); // Host e porta sulla quale kafka è in ascolto
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                    StringSerializer.class); // Classe di serializzazione da utilizzare per serializzare la chiave
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                    StringSerializer.class); // Classe di serializzazione da utilizzare per serializzare i valori
            return props;
        }

        @Bean
        public ProducerFactory<String, String> producerFactory() {
            // implementation for a singleton shared Producer instance.
            return new DefaultKafkaProducerFactory<>(producerConfigs());
        }

        // KafkaTemplate fornisce le utility per inviare messaggi a kafka
        @Bean
        public KafkaTemplate<String, String> kafkaTemplate() {
            return new KafkaTemplate<>(producerFactory());
        }
}
