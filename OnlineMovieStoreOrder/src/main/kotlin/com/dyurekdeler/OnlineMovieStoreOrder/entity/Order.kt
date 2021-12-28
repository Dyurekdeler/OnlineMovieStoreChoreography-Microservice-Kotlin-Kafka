package com.dyurekdeler.OnlineMovieStoreOrder.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Order(
    @Id
    val id: String? = null,
    var customerId: String,
    var movieId: String,
    var quantity: Int,
    var isCanceled: Boolean = false,
    val createdDate: LocalDateTime = LocalDateTime.now(),
    val modifiedDate: LocalDateTime = LocalDateTime.now()
)