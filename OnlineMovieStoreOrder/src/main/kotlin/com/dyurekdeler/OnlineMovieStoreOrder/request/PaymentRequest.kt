package com.dyurekdeler.OnlineMovieStoreCustomer.request

import com.dyurekdeler.OnlineMovieStoreOrder.model.PaymentMethod

class PaymentRequest (
    val orderId: String,
    val paymentMethod: PaymentMethod,
    val isCancelled: Boolean,
)