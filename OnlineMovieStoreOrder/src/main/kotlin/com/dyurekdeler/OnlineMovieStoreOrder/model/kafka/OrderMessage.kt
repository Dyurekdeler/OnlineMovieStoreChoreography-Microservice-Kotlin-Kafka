package com.dyurekdeler.OnlineMovieStoreOrder.model.kafka

import com.dyurekdeler.OnlineMovieStoreOrder.entity.Order


data class OrderMessage(
    val isFailed: Boolean,
    val errorMessage: String? = null,
    val order: Order? = null
)
