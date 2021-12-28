package com.dyurekdeler.OnlineMovieStoreDelivery.request

import com.dyurekdeler.OnlineMovieStoreDelivery.model.DeliveryStatus

class DeliveryRequest (
    val orderId: String,
    val status: DeliveryStatus
)