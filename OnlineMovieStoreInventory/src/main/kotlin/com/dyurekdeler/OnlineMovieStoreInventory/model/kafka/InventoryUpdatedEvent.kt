package com.dyurekdeler.OnlineMovieStoreInventory.model.kafka

import com.dyurekdeler.OnlineMovieStoreInventory.model.ArithmeticOperation
import com.dyurekdeler.OnlineMovieStoreInventory.model.Order

data class InventoryUpdatedEvent(
    val order: Order,
    val operation: ArithmeticOperation
)
