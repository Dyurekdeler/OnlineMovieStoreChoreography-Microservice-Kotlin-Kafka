package com.dyurekdeler.OnlineMovieStoreDelivery.entity

import com.dyurekdeler.OnlineMovieStoreDelivery.model.DeliveryStatus
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Delivery (
    @Id
    val id: String? = null,
    var orderId: String,
    var status: DeliveryStatus,
    val createdDate: LocalDateTime = LocalDateTime.now(),
    var modifiedDate: LocalDateTime = LocalDateTime.now()
)