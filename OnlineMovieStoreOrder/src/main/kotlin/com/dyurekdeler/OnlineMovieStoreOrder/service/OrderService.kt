package com.dyurekdeler.OnlineMovieStoreOrder.service

import com.dyurekdeler.OnlineMovieStoreCustomer.request.PaymentRequest
import com.dyurekdeler.OnlineMovieStoreInventory.request.DeliveryRequest
import com.dyurekdeler.OnlineMovieStoreOrder.client.CustomerClient
import com.dyurekdeler.OnlineMovieStoreOrder.client.DeliveryClient
import com.dyurekdeler.OnlineMovieStoreOrder.client.InventoryClient
import com.dyurekdeler.OnlineMovieStoreOrder.client.PaymentClient
import com.dyurekdeler.OnlineMovieStoreOrder.entity.Order
import com.dyurekdeler.OnlineMovieStoreOrder.model.*
import com.dyurekdeler.OnlineMovieStoreOrder.model.kafka.OrderMessage
import com.dyurekdeler.OnlineMovieStoreOrder.repository.OrderRepository
import com.dyurekdeler.OnlineMovieStoreOrder.request.MovieRequest
import com.dyurekdeler.OnlineMovieStoreOrder.request.OrderRequest
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val customerClient: CustomerClient,
    private val inventoryClient: InventoryClient,
    private val paymentClient: PaymentClient,
    private val deliveryClient: DeliveryClient,
    private val kafkaService: KafkaService

) {

    private val logger = LoggerFactory.getLogger(javaClass)

    fun findById(id: String): Order {
        return orderRepository.findById(id)
            .orElseThrow{ java.lang.Exception("Order with $id is not found") }
    }

    fun createOrder(request: OrderRequest): Order {
        val order = orderRepository.save(
            Order(
                customerId = request.customerId,
                movieId = request.movieId,
                quantity = request.quantity
            )
        )
        return order
    }

    fun updateOrder(id: String, request: OrderRequest): Order {
        val orderToUpdate = findById(id)
        val updatedOrder = orderRepository.save(
            orderToUpdate.apply {
                customerId = request.customerId
                movieId = request.movieId
                quantity = request.quantity
                isCanceled = request.isCancelled
            }
        )
        return updatedOrder
    }

    fun deleteById(id:String) {
        val orderToDelete = findById(id)
        orderRepository.delete(orderToDelete)
    }

    fun placeOrder(request: OrderRequest): Order{

        // validate user
        customerClient.getCustomer(request.customerId.toString())?.let {

            // validate quantity
            val movie = inventoryClient.getMovie(request.movieId)
            if (movie.quantity < request.quantity) throw Exception("Movie has not enough quantity!")

            // insert order
            val order = insertOrder(request)

            // get payment
            val payment: Payment
            try {
                val paymentRequest = PaymentRequest(
                    orderId = order.id.toString(),
                    request.paymentMethod,
                    false
                )
                payment = paymentClient.createPayment(paymentRequest)
                logger.info(">> Processing payment. Payment: $payment")
            } catch (e: Exception) {
                cancelOrder(order)
                logger.error(e.toString())
                throw Exception("Payment process failed!")
            }


            // update quantity
            try {
                logger.info(">> Movie quantity before order processing update. Movie: ${movie.title}, Quantity: ${movie.quantity}")
                updateInventory(movie, request.quantity, false)
            } catch (e: Exception) {
                logger.error(e.toString())
                cancelPayment(payment)
                cancelOrder(order)
                throw Exception("Update inventory process failed!")
            }

            // start delivery
            try {
                val deliveryRequest = DeliveryRequest(
                    order.id!!,
                    DeliveryStatus.Preparing
                )
                deliveryClient.createDelivery(deliveryRequest)
                logger.info(">> Processing delivery")
            } catch (e: Exception) {
                logger.error(e.toString())
                logger.info(">> Movie quantity before cancellation update. Movie: ${movie.title}, Quantity: ${movie.quantity}")
                updateInventory(movie, request.quantity, true)
                logger.info("DELIVERY CATCCH BLOCK $payment")
                cancelPayment(payment)
                cancelOrder(order)
                throw Exception("Start delivery failed!")
            }

            return order

        } ?: throw Exception("Customer not found!")

    }

    fun insertOrder(request: OrderRequest): Order {
        val order = createOrder(request)
        logger.info(">> Inserting order. Order: $order")
        val orderMessage = OrderMessage(
            isFailed = true,
            order = order
        )
        kafkaService.postOrderMessage(orderMessage)
        logger.info(">> Kafka message is sent")
        return order
    }

    fun cancelOrder(order: Order): Order {
        order.isCanceled = true
        orderRepository.save(order)
        logger.info(">>>> Order cancelled. Order: $order")
        return order
    }

    fun cancelPayment(payment: Payment): Payment {
        val paymentRequest = PaymentRequest(
            payment.orderId,
            payment.paymentMethod,
            isCancelled = true
        )
        paymentClient.updatePayment(payment.id, paymentRequest)
        logger.info(">>>> Payment cancelled. Payment: $payment")
        return payment
    }

    fun updateInventory(movie: Movie, quantity: Int, isIncrease: Boolean) {
        val updatedQuantity: Int = if (isIncrease)
            movie.quantity + quantity
        else
            movie.quantity - quantity
        movie.quantity = updatedQuantity
        val movieRequest = MovieRequest(
            title = movie.title,
            about = movie.about,
            duration = movie.duration,
            quantity = updatedQuantity
        )
        inventoryClient.updateMovie(movie.id, movieRequest)
        logger.info(">>> Updated movie quantity. Movie: ${movie.title}, Quantity: ${movie.quantity}")
    }


}