package com.dyurekdeler.OnlineMovieStorePayment.repository

import com.dyurekdeler.OnlineMovieStorePayment.entity.Payment
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PaymentRepository: MongoRepository<Payment, String> {
    fun findByOrderId(id: String): Payment
}