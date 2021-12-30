package com.dyurekdeler.OnlineMovieStoreOrder.service

import com.dyurekdeler.OnlineMovieStoreOrder.entity.Order
import com.dyurekdeler.OnlineMovieStoreOrder.model.kafka.OrderCreatedEvent
import com.dyurekdeler.OnlineMovieStoreOrder.model.OrderStatus
import com.dyurekdeler.OnlineMovieStoreOrder.model.kafka.*
import com.dyurekdeler.OnlineMovieStoreOrder.request.OrderRequest
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service


@Service
class KafkaService(
    private val kafkaTemplate: KafkaTemplate<String, Any>,
    private val orderService: OrderService
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics= ["payment_fails"], groupId = "test_id")
    fun consumePaymentRefundedEvent(event: PaymentRefundedEvent)  {
        // cancel order if payment is refunded to customer
        orderService.updateOrderStatus(event.order.id!!, OrderStatus.Canceled)
        logger.info(">>Order operation is canceled. Triggered event: $event")
    }

    @KafkaListener(topics= ["delivery_events"], groupId = "test_id")
    fun consumeDeliveryCompletedEvent(event: DeliveryCompletedEvent)  {
        // completed order
        orderService.updateOrderStatus(event.order.id!!, OrderStatus.Completed)
        logger.info(">>Order operation is completed successfully. Triggered event: $event")
    }

    fun placeOrder(request: OrderRequest): Order {
        val order = orderService.createOrder(request)
        val orderCreatedEvent = OrderCreatedEvent(
            order,
            request.paymentMethod
        )

        postOrderCreatedEvent(orderCreatedEvent)
        logger.info(">> Order created and event sent. ${orderCreatedEvent}")
        return order
    }

    fun postOrderCreatedEvent(orderCreatedEvent: OrderCreatedEvent){
        "order_events".publish(orderCreatedEvent)
    }

    private fun String.publish(message: Any){
        kafkaTemplate.send(ProducerRecord(this, message))
    }
}