package com.dyurekdeler.OnlineMovieStoreOrder.model

import java.time.LocalDateTime

data class Payment (
    val id: String,
    val orderId: String,
    val paymentMethod: PaymentMethod,
    var isCancelled: Boolean,
    val createdDate: LocalDateTime,
    val modifiedDate: LocalDateTime = LocalDateTime.now()
)
