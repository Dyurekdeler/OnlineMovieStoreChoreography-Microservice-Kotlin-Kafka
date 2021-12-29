package com.dyurekdeler.OnlineMovieStoreOrder.client

import com.dyurekdeler.OnlineMovieStoreCustomer.request.PaymentRequest
import com.dyurekdeler.OnlineMovieStoreOrder.model.Movie
import com.dyurekdeler.OnlineMovieStoreOrder.request.MovieRequest
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.*

@FeignClient(name = "OnlineMovieStoreInventory", url = "\${OnlineMovieStore.server.inventory.url}")
interface InventoryClient {

    @GetMapping("\${OnlineMovieStore.server.inventory.ws.getMovie}")
    fun getMovie(@PathVariable id: String): Movie?

    @PutMapping("\${OnlineMovieStore.server.inventory.ws.updateMovie}")
    fun updateMovie(@PathVariable id: String, @RequestBody request: MovieRequest): Movie


}