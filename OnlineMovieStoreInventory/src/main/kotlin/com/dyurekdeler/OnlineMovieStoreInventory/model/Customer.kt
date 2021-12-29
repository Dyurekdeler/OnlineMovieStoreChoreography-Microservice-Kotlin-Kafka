package com.dyurekdeler.OnlineMovieStoreInventory.model

import java.time.LocalDateTime

data class Customer(
    val id: String,
    val firstname: String,
    val lastname: String,
    val address: String,
    val phone: String,
    val createdDate: LocalDateTime,
    val modifiedDate: LocalDateTime = LocalDateTime.now()
)
