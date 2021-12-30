package com.dyurekdeler.OnlineMovieStoreOrder.service

import com.dyurekdeler.OnlineMovieStoreOrder.client.CustomerClient
import com.dyurekdeler.OnlineMovieStoreOrder.client.InventoryClient
import com.dyurekdeler.OnlineMovieStoreOrder.entity.Order
import com.dyurekdeler.OnlineMovieStoreOrder.model.*
import com.dyurekdeler.OnlineMovieStoreOrder.model.kafka.OrderCreatedEvent
import com.dyurekdeler.OnlineMovieStoreOrder.repository.OrderRepository
import com.dyurekdeler.OnlineMovieStoreOrder.request.OrderRequest
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val customerClient: CustomerClient,
    private val inventoryClient: InventoryClient,
    private val kafkaService: KafkaService

) {

    private val logger = LoggerFactory.getLogger(javaClass)

    fun findById(id: String): Order {
        return orderRepository.findById(id)
            .orElseThrow{ java.lang.Exception("Order with $id is not found") }
    }

    fun createOrder(request: OrderRequest): Order {
        customerClient.getCustomer(request.customerId)?.let { customer ->
            inventoryClient.getMovie(request.movieId)?.let { movie ->
                return orderRepository.save(
                    Order(
                        customer = customer,
                        movie = movie,
                        quantity = request.quantity
                    )
                )
            } ?:  throw Exception("Movie not found!")
        } ?: throw Exception("Customer not found!")

    }

    fun updateOrder(id: String, request: OrderRequest): Order {
        val orderToUpdate = findById(id)
        val updatedOrder = orderRepository.save(
            orderToUpdate.apply {
                quantity = request.quantity
                status = request.status
            }
        )
        return updatedOrder
    }

    fun updateOrderStatus(id: String, orderStatus: OrderStatus): Order {
        val orderToUpdate = findById(id)
        val updatedOrder = orderRepository.save(
            orderToUpdate.apply {
                status = orderStatus
            }
        )
        return updatedOrder
    }

    fun deleteById(id:String) {
        val orderToDelete = findById(id)
        orderRepository.delete(orderToDelete)
    }

    fun placeOrder(request: OrderRequest): Order{
        val order = createOrder(request)
        logger.info(">> Inserting order. Order: $order")

        val orderCreatedEvent = OrderCreatedEvent(
            order,
            request.paymentMethod
        )

        kafkaService.postOrderCreatedEvent(orderCreatedEvent)
        logger.info(">> Kafka message is sent")
        return order
    }

    fun cancelOrder(order: Order): Order {
        order.status = OrderStatus.Canceled
        orderRepository.save(order)
        logger.info(">>>> Order cancelled. Order: $order")
        return order
    }


}