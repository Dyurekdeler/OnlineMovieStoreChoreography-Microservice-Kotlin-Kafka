package com.dyurekdeler.OnlineMovieStoreDelivery.repository

import com.dyurekdeler.OnlineMovieStoreDelivery.entity.Delivery
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface DeliveryRepository: MongoRepository<Delivery, String> {
}