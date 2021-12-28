package com.dyurekdeler.OnlineMovieStoreInventory.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Movie (
    @Id
    val id: String? = null,
    var title: String,
    var duration: Int,
    var about: String,
    var quantity: Int,
    val createdDate: LocalDateTime = LocalDateTime.now(),
    var modifiedDate: LocalDateTime = LocalDateTime.now()
)