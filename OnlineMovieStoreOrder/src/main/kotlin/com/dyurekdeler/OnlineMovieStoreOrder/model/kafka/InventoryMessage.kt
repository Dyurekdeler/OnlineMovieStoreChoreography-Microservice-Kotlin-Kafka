package com.dyurekdeler.OnlineMovieStoreOrder.model.kafka

import com.dyurekdeler.OnlineMovieStoreOrder.model.Movie

data class InventoryMessage(
    val isFailed: Boolean,
    val errorMessage: String? = null,
    val movie: Movie? = null
)
