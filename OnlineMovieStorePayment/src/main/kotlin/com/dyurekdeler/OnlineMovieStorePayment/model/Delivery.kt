package com.dyurekdeler.OnlineMovieStorePayment.model

import java.time.LocalDateTime

data class Delivery (
    val id: String,
    val orderId: String,
    val status: DeliveryStatus,
    val createdDate: LocalDateTime,
    val modifiedDate: LocalDateTime = LocalDateTime.now()
)