package com.dyurekdeler.OnlineMovieStorePayment.model.kafka

import com.dyurekdeler.OnlineMovieStorePayment.model.ArithmeticOperation
import com.dyurekdeler.OnlineMovieStorePayment.model.Order

data class InventoryUpdatedEvent(
    val order: Order,
    val operation: ArithmeticOperation
)
