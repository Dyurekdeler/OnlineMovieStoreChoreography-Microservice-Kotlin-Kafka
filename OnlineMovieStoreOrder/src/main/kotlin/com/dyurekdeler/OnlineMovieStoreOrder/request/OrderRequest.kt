package com.dyurekdeler.OnlineMovieStoreOrder.request

import com.dyurekdeler.OnlineMovieStoreOrder.model.PaymentMethod

class OrderRequest (
    val movieId: String,
    val customerId: String,
    val quantity: Int,
    val paymentMethod: PaymentMethod,
    var isCancelled: Boolean
)