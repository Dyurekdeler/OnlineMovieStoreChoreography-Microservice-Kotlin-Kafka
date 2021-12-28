package com.dyurekdeler.OnlineMovieStorePayment

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class OnlineMovieStorePaymentApplication

fun main(args: Array<String>) {
	runApplication<OnlineMovieStorePaymentApplication>(*args)
}
