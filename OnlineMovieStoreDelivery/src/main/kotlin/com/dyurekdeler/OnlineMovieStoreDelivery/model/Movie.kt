package com.dyurekdeler.OnlineMovieStoreDelivery.model

import java.time.LocalDateTime

data class Movie (
    val id: String,
    val title: String,
    val duration: Int,
    val about: String,
    var quantity: Int,
    val createdDate: LocalDateTime,
    val modifiedDate: LocalDateTime = LocalDateTime.now()
)