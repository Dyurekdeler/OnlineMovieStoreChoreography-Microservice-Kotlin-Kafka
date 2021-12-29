package com.dyurekdeler.OnlineMovieStoreOrder.service

import com.dyurekdeler.OnlineMovieStoreOrder.model.kafka.*
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service


@Service
class KafkaService(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    /**** test methods ****/

    @KafkaListener(topics= ["test_topic"], groupId = "test_id")
    fun consumeTestMessage(message: String)  {
        logger.info("Deniz message received from test topic : $message")
    }

    fun produceTestMessage(message: String) {
        "test_topic".publish(message)
    }


    /*** actual methods ****/

    fun postOrderCreatedEvent(orderCreatedEvent: OrderCreatedEvent){
        "order-events-topic".publish(orderCreatedEvent)
    }

    private fun String.publish(message: Any){
        kafkaTemplate.send(ProducerRecord(this, message))
    }
}