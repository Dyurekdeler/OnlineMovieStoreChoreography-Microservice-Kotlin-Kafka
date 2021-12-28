package com.dyurekdeler.OnlineMovieStoreCustomer.controller

import com.dyurekdeler.OnlineMovieStoreCustomer.entity.Customer
import com.dyurekdeler.OnlineMovieStoreCustomer.repository.CustomerRepository
import com.dyurekdeler.OnlineMovieStoreCustomer.request.CustomerRequest
import com.dyurekdeler.OnlineMovieStoreCustomer.service.CustomerService
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
class CustomerController(
    private val customerService: CustomerService
) {

    @GetMapping("/{id}")
    fun getCustomer(@PathVariable("id") id: String): Customer {
        return customerService.findById(id)
    }

    @PostMapping
    fun createCustomer(@RequestBody request: CustomerRequest): ResponseEntity<Customer> {
        val customer = customerService.createCustomer(request)
        return ResponseEntity(customer, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateCustomer(@PathVariable("id") id: String, @RequestBody request: CustomerRequest, ): ResponseEntity<Customer> {
        val updatedCustomer = customerService.updateCustomer(id, request)
        return ResponseEntity.ok(updatedCustomer)
    }

    @DeleteMapping("/{id}")
    fun deleteCustomer(@PathVariable("id") id: String) {
        customerService.deleteById(id)
    }
}