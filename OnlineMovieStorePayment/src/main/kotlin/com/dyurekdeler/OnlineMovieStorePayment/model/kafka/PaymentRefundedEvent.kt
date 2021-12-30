package com.dyurekdeler.OnlineMovieStorePayment.model.kafka

import com.dyurekdeler.OnlineMovieStorePayment.model.Order

data class PaymentRefundedEvent(
    val order: Order
)
