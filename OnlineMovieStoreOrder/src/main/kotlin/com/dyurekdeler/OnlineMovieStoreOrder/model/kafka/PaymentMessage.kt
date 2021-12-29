package com.dyurekdeler.OnlineMovieStoreOrder.model.kafka

import com.dyurekdeler.OnlineMovieStoreOrder.model.Payment

data class PaymentMessage(
    val isFailed: Boolean,
    val errorMessage: String? = null,
    val payment: Payment? = null
)
