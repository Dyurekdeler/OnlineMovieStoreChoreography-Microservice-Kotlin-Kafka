package com.dyurekdeler.OnlineMovieStoreInventory.service

import com.dyurekdeler.OnlineMovieStoreInventory.model.ArithmeticOperation
import com.dyurekdeler.OnlineMovieStoreInventory.model.kafka.InventoryUpdatedEvent
import com.dyurekdeler.OnlineMovieStoreInventory.model.kafka.PaymentCreatedEvent
import com.dyurekdeler.OnlineMovieStoreInventory.request.MovieRequest
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.LoggerFactory
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaService(
    private val kafkaTemplate: KafkaTemplate<String, Any>,
    private val movieService: MovieService
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics= ["payment-events-topic"], groupId = "test_id")
    fun consumeMessage(event: PaymentCreatedEvent)  {

        // update movie
        event.order.movie.let {
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
            ArithmeticOperation.Subtraction
        )
        postInventoryUpdatedEvent(inventoryUpdatedEvent)

    }

    fun postInventoryUpdatedEvent(inventoryUpdatedEvent: InventoryUpdatedEvent){
        "inventory-events-topic".publish(inventoryUpdatedEvent)
    }

    private fun String.publish(message: Any){
        kafkaTemplate.send(ProducerRecord(this, message))
    }
}