package com.dyurekdeler.OnlineMovieStoreDelivery.service

import com.dyurekdeler.OnlineMovieStoreDelivery.model.DeliveryStatus
import com.dyurekdeler.OnlineMovieStoreDelivery.model.kafka.DeliveryCompletedEvent
import com.dyurekdeler.OnlineMovieStoreDelivery.model.kafka.DeliveryFailedEvent
import com.dyurekdeler.OnlineMovieStoreDelivery.model.kafka.InventoryUpdatedEvent
import com.dyurekdeler.OnlineMovieStoreDelivery.request.DeliveryRequest
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaService(
    private val kafkaTemplate: KafkaTemplate<String, Any>,
    private val deliveryService: DeliveryService
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics= ["inventory_events"], groupId = "test_id")
    fun consumeInventoryDecreasedEvent(event: InventoryUpdatedEvent)  {

        // insert delivery
        val deliveryRequest = DeliveryRequest(
            event.order.id,
            DeliveryStatus.Preparing
        )
        deliveryService.createDelivery(deliveryRequest)

        // assume some time has passed and delivery is delivered to the customer
        // ...
        // update delivery status to Delivered

        // inform order that order process completed
        val deliveryCompletedEvent = DeliveryCompletedEvent(
            event.order
        )
        postDeliveryCompletedEvent(deliveryCompletedEvent)
        logger.info("Delivery completed, informing order $deliveryCompletedEvent")

    }

    fun postDeliveryCompletedEvent(event: DeliveryCompletedEvent){
        "delivery_events".publish(event)
    }

    // rollback scenario manually triggered
    fun postDeliveryFailedvent(event: DeliveryFailedEvent){
        "delivery_fails".publish(event)
    }

    private fun String.publish(message: Any){
        kafkaTemplate.send(ProducerRecord(this, message))
    }
}