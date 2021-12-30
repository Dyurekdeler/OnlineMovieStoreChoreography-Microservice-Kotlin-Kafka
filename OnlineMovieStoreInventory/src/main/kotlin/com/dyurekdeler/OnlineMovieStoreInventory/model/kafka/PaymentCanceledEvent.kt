package com.dyurekdeler.OnlineMovieStoreInventory.model.kafka

import com.dyurekdeler.OnlineMovieStoreInventory.model.Order

data class PaymentCanceledEvent(
    val order: Order
)
