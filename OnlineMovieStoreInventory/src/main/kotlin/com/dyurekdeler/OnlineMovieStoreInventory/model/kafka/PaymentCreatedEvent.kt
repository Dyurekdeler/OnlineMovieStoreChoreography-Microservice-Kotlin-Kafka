package com.dyurekdeler.OnlineMovieStoreInventory.model.kafka

import com.dyurekdeler.OnlineMovieStoreInventory.model.Order

data class PaymentCreatedEvent(
    val order: Order
)
