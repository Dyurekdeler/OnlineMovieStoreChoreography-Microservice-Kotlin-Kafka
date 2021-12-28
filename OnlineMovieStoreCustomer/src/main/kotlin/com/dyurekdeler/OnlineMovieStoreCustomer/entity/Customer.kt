package com.dyurekdeler.OnlineMovieStoreCustomer.entity

import org.springframework.data.annotation.Id
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Customer (
    @Id
    val id: String? = null,
    var firstname: String,
    var lastname: String,
    var address: String,
    var phone: String,
    val createdDate: LocalDateTime = LocalDateTime.now(),
    val modifiedDate: LocalDateTime = LocalDateTime.now()
)