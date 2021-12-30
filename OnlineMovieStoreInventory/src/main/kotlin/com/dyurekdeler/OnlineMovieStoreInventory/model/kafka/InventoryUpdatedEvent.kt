package com.dyurekdeler.OnlineMovieStoreInventory.model.kafka

import com.dyurekdeler.OnlineMovieStoreInventory.model.Order

data class InventoryUpdatedEvent(
    val order: Order,
    val appliedOperation: InventoryOperation
){
    enum class InventoryOperation{
        Decrease,
        Increase,
        Nothing
    }
}
