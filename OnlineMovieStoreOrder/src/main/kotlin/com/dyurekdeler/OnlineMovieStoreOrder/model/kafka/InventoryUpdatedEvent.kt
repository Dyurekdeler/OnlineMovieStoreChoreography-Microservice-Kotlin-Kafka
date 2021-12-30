package com.dyurekdeler.OnlineMovieStoreOrder.model.kafka

import com.dyurekdeler.OnlineMovieStoreOrder.entity.Order
import com.dyurekdeler.OnlineMovieStoreOrder.model.ArithmeticOperation

data class InventoryUpdatedEvent(
    val order: Order,
    val operation: ArithmeticOperation,
    val isFailed: Boolean
)
