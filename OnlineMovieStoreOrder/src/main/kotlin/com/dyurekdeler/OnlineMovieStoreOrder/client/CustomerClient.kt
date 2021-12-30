package com.dyurekdeler.OnlineMovieStoreOrder.client

import com.dyurekdeler.OnlineMovieStoreOrder.model.Customer
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "OnlineMovieStoreCustomer", url = "\${OnlineMovieStore.server.customer.url}")
interface CustomerClient {

    @GetMapping("\${OnlineMovieStore.server.customer.ws.getCustomer}")
    fun getCustomer(@PathVariable id: String): Customer?
}