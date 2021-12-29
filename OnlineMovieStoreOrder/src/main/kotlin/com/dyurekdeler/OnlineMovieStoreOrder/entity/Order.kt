package com.dyurekdeler.OnlineMovieStoreOrder.entity

import com.dyurekdeler.OnlineMovieStoreOrder.model.Customer
import com.dyurekdeler.OnlineMovieStoreOrder.model.Movie
import com.dyurekdeler.OnlineMovieStoreOrder.model.OrderStatus
import com.dyurekdeler.OnlineMovieStoreOrder.service.OrderService
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Order(
    @Id
    val id: String? = null,
    var customer: Customer,
    var movie: Movie,
    var quantity: Int,
    var status: OrderStatus = OrderStatus.Created,
    val createdDate: LocalDateTime = LocalDateTime.now(),
    val modifiedDate: LocalDateTime = LocalDateTime.now()
)