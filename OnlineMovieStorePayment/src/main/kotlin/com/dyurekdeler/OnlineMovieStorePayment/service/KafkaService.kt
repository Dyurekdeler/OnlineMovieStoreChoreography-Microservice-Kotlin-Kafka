package com.dyurekdeler.OnlineMovieStorePayment.service

import com.dyurekdeler.OnlineMovieStorePayment.model.kafka.*
import com.dyurekdeler.OnlineMovieStorePayment.repository.PaymentRepository
import com.dyurekdeler.OnlineMovieStorePayment.request.PaymentRequest
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaService(
    private val kafkaTemplate: KafkaTemplate<String, Any>,
    private val paymentService: PaymentService,
    private val paymentRepository: PaymentRepository
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

    @KafkaListener(topics= ["inventory-events-topic"], groupId = "test_id")
    fun consumeInventoryUpdatedEvent(event: InventoryUpdatedEvent)  {
        logger.info("Deniz message received from payment topic : $event")

        // inventory restored means payment must be refunded to customer
        // update payment status to canceled
        // first find the payment
        val payment = paymentRepository.findByOrderId(event.order.id)

        payment.id?.let {
            paymentService.updatePaymentStatus(it, isCancelled = true)
        }

        // payment refund operation means order is canceled
        // payment failed event === payment canceled event
        val paymentFailedEvent = PaymentCanceledEvent(
            event.order
        )
        postPaymentFailedEvent(paymentFailedEvent)

    }

    fun postPaymentCreatedEvent(paymentCreatedEvent: PaymentCreatedEvent){
        "payment-events-topic".publish(paymentCreatedEvent)
    }

    // this rollback scenario is not binded to any contoller or service yet
    fun postPaymentFailedEvent(paymentCanceledEvent: PaymentCanceledEvent){
        "payment-events-topic".publish(paymentCanceledEvent)
    }


    private fun String.publish(message: Any){
        kafkaTemplate.send(ProducerRecord(this, message))
    }
}