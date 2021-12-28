package com.dyurekdeler.OnlineMovieStorePayment.controller

import com.dyurekdeler.OnlineMovieStorePayment.entity.Payment
import com.dyurekdeler.OnlineMovieStorePayment.repository.PaymentRepository
import com.dyurekdeler.OnlineMovieStorePayment.request.PaymentRequest
import com.dyurekdeler.OnlineMovieStorePayment.service.PaymentService
import org.bson.types.ObjectId
import org.slf4j.LoggerFactory
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.Exception
import java.time.LocalDateTime

@RestController
class PaymentController(
    private val paymentService: PaymentService
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/{id}")
    fun getPayment(@PathVariable("id") id: String): Payment {
        return paymentService.findById(id)
    }

    @PostMapping()
    fun createPayment(@RequestBody request: PaymentRequest): Payment {
        return paymentService.createPayment(request)
    }

    @PutMapping("/{id}")
    fun updatePayment(@RequestBody request: PaymentRequest, @PathVariable("id") id: String): Payment {
        return paymentService.updatePayment(id, request)
    }

    @DeleteMapping("/{id}")
    fun deletePayment(@PathVariable("id") id: String) {
        return paymentService.deleteById(id)
    }
}