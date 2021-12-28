package com.dyurekdeler.OnlineMovieStoreOrder.client

import com.dyurekdeler.OnlineMovieStoreCustomer.request.PaymentRequest
import com.dyurekdeler.OnlineMovieStoreOrder.model.Payment
import org.bson.types.ObjectId
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "OnlineMovieStorePayment", url = "\${OnlineMovieStore.server.payment.url}")
interface PaymentClient {

    @PostMapping("\${OnlineMovieStore.server.payment.ws.createPayment}")
    fun createPayment(request: PaymentRequest): Payment

    @PutMapping("\${OnlineMovieStore.server.payment.ws.updatePayment}")
    fun updatePayment(@PathVariable id: String, @RequestBody request: PaymentRequest): Payment

}