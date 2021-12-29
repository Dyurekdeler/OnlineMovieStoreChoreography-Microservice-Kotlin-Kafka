package com.dyurekdeler.OnlineMovieStorePayment.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

data class Order(
    val id: String,
    var customer: Customer,
    var movie: Movie,
    var quantity: Int,
    var isCanceled: Boolean
)