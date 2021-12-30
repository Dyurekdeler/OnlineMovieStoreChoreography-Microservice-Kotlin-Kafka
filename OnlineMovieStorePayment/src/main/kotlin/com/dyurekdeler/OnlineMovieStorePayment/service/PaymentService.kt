package com.dyurekdeler.OnlineMovieStorePayment.service

import com.dyurekdeler.OnlineMovieStorePayment.entity.Payment
import com.dyurekdeler.OnlineMovieStorePayment.repository.PaymentRepository
import com.dyurekdeler.OnlineMovieStorePayment.request.PaymentRequest
import org.bson.types.ObjectId
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.lang.Exception
import java.time.LocalDateTime

@Service
class PaymentService(
    private val paymentRepository: PaymentRepository
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    fun findById(id: String): Payment {
        return paymentRepository.findById(id)
            .orElseThrow{ Exception("Payment with $id is not found") }
    }

    fun createPayment(request: PaymentRequest): Payment {
        val payment = paymentRepository.save(
            Payment(
                orderId = request.orderId,
                paymentMethod = request.paymentMethod,
                isCancelled = request.isCancelled
            )
        )
        logger.info("Payment created is $payment")
        return payment
    }

    fun updatePayment(id: String, request: PaymentRequest): Payment {
        val paymentToUpdate = findById(id)
        val updatedPayment = paymentRepository.save(
            paymentToUpdate.apply {
                orderId = request.orderId
                paymentMethod = request.paymentMethod
                isCancelled = request.isCancelled
                modifiedDate = LocalDateTime.now()
            }
        )
        return updatedPayment
    }

    fun updatePaymentStatus(id: String, isCancelled: Boolean): Payment {
        val paymentToUpdate = findById(id)
        val updatedPayment = paymentRepository.save(
            paymentToUpdate.apply {
                this.isCancelled = isCancelled
            }
        )
        return updatedPayment
    }

    fun deleteById(id:String) {
        val paymentToDelete = findById(id)
        paymentRepository.delete(paymentToDelete)
    }

}