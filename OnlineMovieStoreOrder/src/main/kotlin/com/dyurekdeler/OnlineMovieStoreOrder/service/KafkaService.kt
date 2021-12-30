package com.dyurekdeler.OnlineMovieStoreOrder.service

import com.dyurekdeler.OnlineMovieStoreOrder.model.OrderStatus
import com.dyurekdeler.OnlineMovieStoreOrder.model.kafka.*
import com.dyurekdeler.OnlineMovieStoreOrder.repository.OrderRepository
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

    /**** test methods ****/

    @KafkaListener(topics= ["test_topic"], groupId = "test_id")
    fun consumeTestMessage(message: String)  {
        logger.info("Deniz message received from test topic : $message")
    }

    fun produceTestMessage(message: String) {
        "test_topic".publish(message)
    }

    /*** actual methods ****/

    fun postOrderCreatedEvent(orderCreatedEvent: OrderCreatedEvent){
        "order-events-topic".publish(orderCreatedEvent)
    }

    @KafkaListener(topics= ["inventory-events-topic"], groupId = "test_id")
    fun consumeMessage(event: NotEnoughQuantityEvent)  {
        // cancel order due fail
        orderService.updateOrderStatus(event.order.id!!, OrderStatus.Canceled)

    }

    @KafkaListener(topics= ["payment-events-topic"], groupId = "test_id")
    fun consumeMessage(event: PaymentCanceledEvent)  {
        // cancel order due fail
        orderService.updateOrderStatus(event.order.id!!, OrderStatus.Canceled)
    }

    @KafkaListener(topics= ["delivery-events-topic"], groupId = "test_id")
    fun consumeDeliveryCompletedMessage(event: PaymentCanceledEvent)  {
        // completed order
        orderService.updateOrderStatus(event.order.id!!, OrderStatus.Completed)
    }

    private fun String.publish(message: Any){
        kafkaTemplate.send(ProducerRecord(this, message))
    }
}