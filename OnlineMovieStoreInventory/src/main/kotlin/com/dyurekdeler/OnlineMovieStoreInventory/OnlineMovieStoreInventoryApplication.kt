package com.dyurekdeler.OnlineMovieStoreInventory

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class OnlineMovieStoreInventoryApplication

fun main(args: Array<String>) {
	runApplication<OnlineMovieStoreInventoryApplication>(*args)
}
