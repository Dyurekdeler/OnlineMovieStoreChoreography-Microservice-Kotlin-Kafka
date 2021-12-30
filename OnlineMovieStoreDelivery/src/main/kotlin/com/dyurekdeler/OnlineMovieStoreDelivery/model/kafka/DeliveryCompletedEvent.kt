package com.dyurekdeler.OnlineMovieStoreDelivery.model.kafka

import com.dyurekdeler.OnlineMovieStoreDelivery.model.ArithmeticOperation
import com.dyurekdeler.OnlineMovieStoreDelivery.model.Order

data class DeliveryCompletedEvent(
    val order: Order
)
