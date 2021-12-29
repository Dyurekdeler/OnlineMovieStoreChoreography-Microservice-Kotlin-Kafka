package com.dyurekdeler.OnlineMovieStoreCustomer.request

import com.dyurekdeler.OnlineMovieStoreOrder.entity.Order
import com.dyurekdeler.OnlineMovieStoreOrder.model.PaymentMethod

class PaymentRequest (
    val order: Order,
    val paymentMethod: PaymentMethod,
    val isCancelled: Boolean,
)