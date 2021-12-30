package com.dyurekdeler.OnlineMovieStoreOrder.model.kafka

import com.dyurekdeler.OnlineMovieStoreOrder.entity.Order
import com.dyurekdeler.OnlineMovieStoreOrder.model.PaymentMethod

data class OrderCreatedEvent(
    val order: Order,
    val paymentMethod: PaymentMethod
)