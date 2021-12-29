package com.dyurekdeler.OnlineMovieStoreDelivery.model

import com.dyurekdeler.OnlineMovieStoreDelivery.model.Customer
import com.dyurekdeler.OnlineMovieStoreDelivery.model.Movie
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