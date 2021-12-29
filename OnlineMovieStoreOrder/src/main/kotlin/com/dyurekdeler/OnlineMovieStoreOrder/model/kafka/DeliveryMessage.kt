package com.dyurekdeler.OnlineMovieStoreOrder.model.kafka

import com.dyurekdeler.OnlineMovieStoreOrder.model.Delivery

data class DeliveryMessage(
    val isFailed: Boolean,
    val errorMessage: String? = null,
    val delivery: Delivery? = null
)
