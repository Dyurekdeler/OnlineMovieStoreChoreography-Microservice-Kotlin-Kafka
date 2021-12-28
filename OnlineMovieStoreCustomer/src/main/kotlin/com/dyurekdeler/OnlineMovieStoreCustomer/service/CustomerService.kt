package com.dyurekdeler.OnlineMovieStoreCustomer.service

import com.dyurekdeler.OnlineMovieStoreCustomer.entity.Customer
import com.dyurekdeler.OnlineMovieStoreCustomer.repository.CustomerRepository
import com.dyurekdeler.OnlineMovieStoreCustomer.request.CustomerRequest
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class CustomerService(
    private val customerRepository: CustomerRepository
) {
    fun findById(id: String): Customer {
        return customerRepository.findById(id)
            .orElseThrow{ Exception("Customer with $id is not found") }
    }

    fun createCustomer(request: CustomerRequest): Customer {
        val customer = customerRepository.save(
            Customer(
                firstname = request.firstname,
                lastname = request.lastname,
                address = request.address,
                phone = request.phone,
            )
        )
        return customer
    }

    fun updateCustomer(id: String, request: CustomerRequest): Customer {
        val customerToUpdate = findById(id)
        val updatedCustomer = customerRepository.save(
            customerToUpdate.apply {
                firstname = request.firstname
                lastname = request.lastname
                address = request.address
                phone = request.phone
            }
        )
        return updatedCustomer
    }

    fun deleteById(id:String) {
        val customerToDelete = findById(id)
        customerRepository.delete(customerToDelete)
    }
}