package com.dyurekdeler.OnlineMovieStoreOrder.service

import com.dyurekdeler.OnlineMovieStoreOrder.model.kafka.DeliveryMessage
import com.dyurekdeler.OnlineMovieStoreOrder.model.kafka.InventoryMessage
import com.dyurekdeler.OnlineMovieStoreOrder.model.kafka.OrderMessage
import com.dyurekdeler.OnlineMovieStoreOrder.model.kafka.PaymentMessage
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import java.util.concurrent.Future

@Service
class KafkaService(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics= ["test_topic"], groupId = "test_id")
    fun consumeTestMessage(message: String)  {
        logger.info("Deniz message received from test topic : $message")
    }

    fun produceTestMessage(message: String) {
        "test_topic".publish(message)
    }

    @KafkaListener(topics= ["inventory-topic"], groupId = "test_id")
    fun consumeInventoryMessage(message: InventoryMessage)  {
        logger.info("Deniz message received from inventory topic : $message")

        if (message.isFailed) {
            // rollback previous step
        } else {
            // publish new event for next step
        }
    }

    @KafkaListener(topics= ["payment-topic"], groupId = "test_id")
    fun consumePaymentMessage(message: PaymentMessage)  {
        logger.info("Deniz message received from payment topic : $message")

        if (message.isFailed) {
            // rollback previous step
        } else {
            // publish new event for next step
        }
    }

    @KafkaListener(topics= ["delivery-topic"], groupId = "test_id")
    fun consumeDeliveryMessage(message: DeliveryMessage)  {
        logger.info("Deniz message received from delivery topic : $message")

        if (message.isFailed) {
            // rollback previous step
        } else {
            // publish new event for next step
        }
    }

    fun postOrderMessage(orderMessage: OrderMessage){
        "payment-topic".publish(orderMessage)
    }

    private fun String.publish(message: Any){
        kafkaTemplate.send(ProducerRecord(this, message))
    }
}