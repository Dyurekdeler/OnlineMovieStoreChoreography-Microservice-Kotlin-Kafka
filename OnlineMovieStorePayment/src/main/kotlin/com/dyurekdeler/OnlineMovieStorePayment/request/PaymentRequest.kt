package com.dyurekdeler.OnlineMovieStorePayment.request

import com.dyurekdeler.OnlineMovieStorePayment.model.PaymentMethod
import org.bson.types.ObjectId

class PaymentRequest (
    val orderId: String,
    val paymentMethod: PaymentMethod,
    val isCancelled: Boolean,
)