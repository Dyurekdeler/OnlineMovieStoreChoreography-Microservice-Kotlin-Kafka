package com.dyurekdeler.OnlineMovieStoreDelivery.model.kafka

import com.dyurekdeler.OnlineMovieStoreDelivery.model.Order

data class InventoryUpdatedEvent(
    val order: Order,
    val appliedOperation: InventoryOperation
){
    enum class InventoryOperation{
        Decrease,
        Increase
    }
}