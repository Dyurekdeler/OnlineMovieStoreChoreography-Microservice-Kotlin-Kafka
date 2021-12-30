package com.dyurekdeler.OnlineMovieStoreDelivery.model.kafka

import com.dyurekdeler.OnlineMovieStoreDelivery.model.Order

data class DeliveryFailedEvent(
    val order: Order
)
