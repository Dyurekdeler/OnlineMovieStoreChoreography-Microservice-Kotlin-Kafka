package com.dyurekdeler.OnlineMovieStoreInventory.service

import com.dyurekdeler.OnlineMovieStoreInventory.model.kafka.*
import com.dyurekdeler.OnlineMovieStoreInventory.request.MovieRequest
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaService(
    private val kafkaTemplate: KafkaTemplate<String, Any>,
    private val movieService: MovieService
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics= ["payment_events"], groupId = "test_id")
    fun consumePaymentCreatedEvent(event: PaymentCreatedEvent)  {

        // check quantity then update movie
        event.order.movie.let {
            if (it.quantity < event.order.quantity) {
                // publish a rollback msg to order service
                val inventoryUpdatedEvent = InventoryUpdatedEvent(
                    event.order,
                    InventoryUpdatedEvent.InventoryOperation.Nothing
                )
                postNotEnoughQuantityEvent(inventoryUpdatedEvent)
                logger.info(">>Not enough quantity! Publishing event $inventoryUpdatedEvent")
                return
            }

            val movieRequest = MovieRequest(
                title = it.title,
                duration = it.duration,
                about = it.about,
                quantity = (it.quantity - event.order.quantity)
            )
            movieService.updateMovie(it.id,movieRequest )
        }

        // publish ready msg to delivery service
        val inventoryUpdatedEvent = InventoryUpdatedEvent(
            event.order,
            InventoryUpdatedEvent.InventoryOperation.Decrease
        )

        postInventoryUpdatedEvent(inventoryUpdatedEvent)
        logger.info(">>Inventory is descreased by quantity. Publishing event: $inventoryUpdatedEvent")


    }

    @KafkaListener(topics= ["delivery_fails"], groupId = "test_id")
    fun consumeDeliveryFailedEvent(event: DeliveryFailedEvent)  {

        // revert the subtraction from quantity because delivery faied
                event.order.movie.let {
                    val movieRequest = MovieRequest(
                        title = it.title,
                        duration = it.duration,
                        about = it.about,
                        quantity = (it.quantity + event.order.quantity)
                    )
                    movieService.updateMovie(it.id,movieRequest)
                }

        // publish inventory restored msg to payment service
        // so that payment can be refuneded to customer
        val inventoryUpdatedEvent = InventoryUpdatedEvent(
            event.order,
            InventoryUpdatedEvent.InventoryOperation.Increase
        )
        postInventoryRollbackEvent(inventoryUpdatedEvent)
        logger.info(">>Inventory is increased due to rollback because delivery failed. Publishing event: $inventoryUpdatedEvent")


    }

    fun postInventoryUpdatedEvent(inventoryUpdatedEvent: InventoryUpdatedEvent){
        "inventory_events".publish(inventoryUpdatedEvent)
    }

    fun postNotEnoughQuantityEvent(event: InventoryUpdatedEvent){
        "inventory_fails".publish(event)
    }

    fun postInventoryRollbackEvent(event: InventoryUpdatedEvent){
        "inventory_fails".publish(event)
    }


    private fun String.publish(message: Any){
        kafkaTemplate.send(ProducerRecord(this, message))
    }
}