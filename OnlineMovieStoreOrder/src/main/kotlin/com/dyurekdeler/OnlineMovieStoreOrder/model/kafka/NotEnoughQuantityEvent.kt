package com.dyurekdeler.OnlineMovieStoreOrder.model.kafka

import com.dyurekdeler.OnlineMovieStoreOrder.entity.Order

data class NotEnoughQuantityEvent(
    val order: Order
)
