package com.dyurekdeler.OnlineMovieStoreDelivery.service

import com.dyurekdeler.OnlineMovieStoreDelivery.model.DeliveryStatus
import com.dyurekdeler.OnlineMovieStoreDelivery.model.kafka.DeliveryCompletedEvent
import com.dyurekdeler.OnlineMovieStoreDelivery.model.kafka.DeliveryFailedEvent
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

        // inform order that order process completed
        val deliveryCompletedEvent = DeliveryCompletedEvent(
            event.order
        )
        postDeliveryCompletedEvent(deliveryCompletedEvent)

    }

    fun postDeliveryCompletedEvent(deliveryCompletedEvent: DeliveryCompletedEvent){
        "delivery-events-topic".publish(deliveryCompletedEvent)
    }

    // rollback scenario not binded to any controller or service yet
    fun postDeliveryFailedEvent(deliveryFailedEvent: DeliveryFailedEvent){
        "delivery-events-topic".publish(deliveryFailedEvent)
    }

    private fun String.publish(message: Any){
        kafkaTemplate.send(ProducerRecord(this, message))
    }
}