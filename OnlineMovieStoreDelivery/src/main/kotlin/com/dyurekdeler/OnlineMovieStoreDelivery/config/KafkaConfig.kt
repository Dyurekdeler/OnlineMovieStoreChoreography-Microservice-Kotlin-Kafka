package com.dyurekdeler.OnlineMovieStoreDelivery.config

import com.dyurekdeler.OnlineMovieStoreDelivery.serializer.KafkaModelDeserializer
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.*
import org.springframework.kafka.support.serializer.JsonSerializer

@Configuration
class KafkaConfig(
    private val objectMapper: ObjectMapper,
    private val properties: KafkaProperties,
    private val deserializer: KafkaModelDeserializer
){
    @Bean
    fun producerFactory(): ProducerFactory<String, Any> {
        val factory = DefaultKafkaProducerFactory<String, Any>(properties.buildProducerProperties())
        factory.setValueSerializer(JsonSerializer(objectMapper))
        return factory
    }

    @Bean
    fun consumerFactory(): ConsumerFactory<Any, Any> {
        val factory = DefaultKafkaConsumerFactory<Any, Any>(properties.buildConsumerProperties())
        factory.setValueDeserializer(deserializer)
        return factory
    }

    @Bean
    fun kafkaTemplate(producerFactory: ProducerFactory<*,*>): KafkaTemplate<*,*> {
        return KafkaTemplate(producerFactory)
    }

}