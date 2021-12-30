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

    @KafkaListener(topics= ["order_events"], groupId = "test_id")
    fun consumeOrderCreatedEvent(event: OrderCreatedEvent)  {

        // insert payment
        val paymentRequest = PaymentRequest(
            event.order.id,
            event.paymentMethod,
            false
        )
        paymentService.createPayment(paymentRequest)

        val paymentCreatedEvent = PaymentCreatedEvent(
            event.order
        )
        postPaymentCreatedEvent(paymentCreatedEvent)
        logger.info(">>Payment created and publishing event. $paymentCreatedEvent")
    }

    @KafkaListener(topics= ["inventory_fails"], groupId = "test_id")
    fun consumeInventoryIncreasedEvent(event: InventoryUpdatedEvent)  {
        // inventory restored means payment must be refunded to customer
        // update payment status to canceled
        // first find the payment
        val payment = paymentRepository.findByOrderId(event.order.id)

        payment.id?.let {
            paymentService.updatePaymentStatus(it, isCancelled = true)
        }

        val paymentRefundedEvent = PaymentRefundedEvent(
            event.order
        )
        postPaymentRefundedEvent(paymentRefundedEvent)
        logger.info(">>Payment is refunded due inventory rollback. Publishing event. $paymentRefundedEvent")


    }

    fun postPaymentCreatedEvent(event: PaymentCreatedEvent){
        "payment_events".publish(event)
    }

    fun postPaymentRefundedEvent(event: PaymentRefundedEvent){
        "payment_fails".publish(event)
    }

    private fun String.publish(message: Any){
        kafkaTemplate.send(ProducerRecord(this, message))
    }
}