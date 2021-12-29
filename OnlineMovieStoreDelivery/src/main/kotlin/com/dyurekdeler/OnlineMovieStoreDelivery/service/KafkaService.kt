package com.dyurekdeler.OnlineMovieStoreDelivery.service

import com.dyurekdeler.OnlineMovieStoreDelivery.model.DeliveryStatus
import com.dyurekdeler.OnlineMovieStoreDelivery.model.kafka.InventoryUpdatedEvent
import com.dyurekdeler.OnlineMovieStoreDelivery.request.DeliveryRequest
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.LoggerFactory
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaService(
    private val kafkaTemplate: KafkaTemplate<String, Any>,
    private val deliveryService: DeliveryService
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics= ["inventory-events-topic"], groupId = "test_id")
    fun consumeMessage(event: InventoryUpdatedEvent)  {

        // insert delivery
        val deliveryRequest = DeliveryRequest(
            event.order.id,
            DeliveryStatus.Preparing
        )
        deliveryService.createDelivery(deliveryRequest)

        // maybe publish operation completed message to order ??

    }

    fun postInventoryUpdatedEvent(inventoryUpdatedEvent: InventoryUpdatedEvent){
        "inventory-events-topic".publish(inventoryUpdatedEvent)
    }

    private fun String.publish(message: Any){
        kafkaTemplate.send(ProducerRecord(this, message))
    }
}