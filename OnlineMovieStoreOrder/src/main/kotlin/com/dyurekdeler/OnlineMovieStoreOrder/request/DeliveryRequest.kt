package com.dyurekdeler.OnlineMovieStoreInventory.request

import com.dyurekdeler.OnlineMovieStoreOrder.model.DeliveryStatus

class DeliveryRequest (
    val orderId: String,
    val status: DeliveryStatus
)