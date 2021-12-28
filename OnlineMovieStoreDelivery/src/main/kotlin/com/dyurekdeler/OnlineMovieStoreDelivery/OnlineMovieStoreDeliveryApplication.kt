package com.dyurekdeler.OnlineMovieStoreDelivery

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient


@SpringBootApplication
@EnableEurekaClient
class OnlineMovieStoreDeliveryApplication

fun main(args: Array<String>) {
	runApplication<OnlineMovieStoreDeliveryApplication>(*args)
}
