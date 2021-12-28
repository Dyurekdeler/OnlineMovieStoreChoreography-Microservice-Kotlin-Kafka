package com.dyurekdeler.OnlineMovieStoreOrder.client

import com.dyurekdeler.OnlineMovieStoreCustomer.request.PaymentRequest
import com.dyurekdeler.OnlineMovieStoreInventory.request.DeliveryRequest
import com.dyurekdeler.OnlineMovieStoreOrder.model.Delivery
import com.dyurekdeler.OnlineMovieStoreOrder.model.Payment
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "OnlineMovieStoreDelivery", url = "\${OnlineMovieStore.server.delivery.url}")
interface DeliveryClient {

    @PostMapping("\${OnlineMovieStore.server.delivery.ws.createDelivery}")
    fun createDelivery(request: DeliveryRequest): Delivery

}