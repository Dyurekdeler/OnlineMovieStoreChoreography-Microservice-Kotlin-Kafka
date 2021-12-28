package com.dyurekdeler.OnlineMovieStorePayment.entity

import com.dyurekdeler.OnlineMovieStorePayment.model.PaymentMethod
import org.springframework.data.annotation.Id
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Payment (
    @Id
    val id: String? = null,
    var orderId: String,
    var paymentMethod: PaymentMethod,
    var isCancelled: Boolean,
    val createdDate: LocalDateTime = LocalDateTime.now(),
    var modifiedDate: LocalDateTime = LocalDateTime.now()
)