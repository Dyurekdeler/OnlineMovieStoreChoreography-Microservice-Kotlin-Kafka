package com.dyurekdeler.OnlineMovieStoreCustomer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class OnlineMovieStoreCustomerApplication

fun main(args: Array<String>) {
	runApplication<OnlineMovieStoreCustomerApplication>(*args)
}
