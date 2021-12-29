package com.dyurekdeler.OnlineMovieStorePayment.service

import com.dyurekdeler.OnlineMovieStorePayment.model.kafka.OrderCreatedEvent
import com.dyurekdeler.OnlineMovieStorePayment.model.kafka.PaymentCreatedEvent
import com.dyurekdeler.OnlineMovieStorePayment.request.PaymentRequest
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaService(
    private val kafkaTemplate: KafkaTemplate<String, Any>,
    private val paymentService: PaymentService
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics= ["order-events-topic"], groupId = "test_id")
    fun consumeEvent(event: OrderCreatedEvent)  {
        logger.info("Deniz message received from payment topic : $event")

        // insert payment
        val paymentRequest = PaymentRequest(
            event.order.id,
            event.paymentMethod,
            false
        )
        paymentService.createPayment(paymentRequest)

        // publish ready msg to inventory update
        val paymentCreatedEvent = PaymentCreatedEvent(
            event.order
        )
        postPaymentCreatedEvent(paymentCreatedEvent)

    }

    fun postPaymentCreatedEvent(paymentCreatedEvent: PaymentCreatedEvent){
        "payment-events-topic".publish(paymentCreatedEvent)
    }

    private fun String.publish(message: Any){
        kafkaTemplate.send(ProducerRecord(this, message))
    }
}