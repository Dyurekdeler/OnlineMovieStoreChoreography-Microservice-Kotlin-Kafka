package com.dyurekdeler.OnlineMovieStoreDelivery.controller

import com.dyurekdeler.OnlineMovieStoreDelivery.entity.Delivery
import com.dyurekdeler.OnlineMovieStoreDelivery.request.DeliveryRequest
import com.dyurekdeler.OnlineMovieStoreDelivery.service.DeliveryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class DeliveryController(
    private val deliveryService: DeliveryService
) {

    @GetMapping("/{id}")
    fun getDelivery(@PathVariable("id") id: String): ResponseEntity<Delivery> {
        val delivery =deliveryService.findById(id)
        return ResponseEntity.ok(delivery)
    }

    @PostMapping()
    fun createDelivery(@RequestBody request: DeliveryRequest): Delivery {
        val delivery = deliveryService.createDelivery(request)
        // throw Exception("BAD REQUEST ON PURPOSE FOR ROLLBACK SCENARIO")
        return delivery
    }

    @PutMapping("/{id}")
    fun updateDelivery(@RequestBody request: DeliveryRequest, @PathVariable("id") id: String): ResponseEntity<Delivery> {
        val updatedDelivery = deliveryService.updateDelivery(id, request)
        return ResponseEntity.ok(updatedDelivery)
    }

    @DeleteMapping("/{id}")
    fun deleteDelivery(@PathVariable("id") id: String) {
        deliveryService.deleteById(id)
    }

}