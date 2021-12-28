package com.dyurekdeler.OnlineMovieStoreDelivery.service

import com.dyurekdeler.OnlineMovieStoreDelivery.entity.Delivery
import com.dyurekdeler.OnlineMovieStoreDelivery.repository.DeliveryRepository
import com.dyurekdeler.OnlineMovieStoreDelivery.request.DeliveryRequest
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.lang.Exception
import java.time.LocalDateTime

@Service
class DeliveryService(
    private val deliveryRepository: DeliveryRepository
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    fun findById(id: String): Delivery {
        return deliveryRepository.findById(id)
            .orElseThrow{ Exception("Delivery with $id is not found") }
    }

    fun createDelivery(request: DeliveryRequest): Delivery {
        val delivery = deliveryRepository.save(
            Delivery(
                orderId = request.orderId,
                status = request.status,
            )
        )
        logger.info("Delivery created is $delivery")
        return delivery
    }

    fun updateDelivery(id: String, request: DeliveryRequest): Delivery {
        val deliveryToUpdate = findById(id)
        val updatedDelivery = deliveryRepository.save(
            deliveryToUpdate.apply {
                orderId = request.orderId
                status = request.status
                modifiedDate = LocalDateTime.now()
            }
        )
        return updatedDelivery
    }

    fun deleteById(id:String) {
        val deliveryToDelete = findById(id)
        deliveryRepository.delete(deliveryToDelete)
    }

}