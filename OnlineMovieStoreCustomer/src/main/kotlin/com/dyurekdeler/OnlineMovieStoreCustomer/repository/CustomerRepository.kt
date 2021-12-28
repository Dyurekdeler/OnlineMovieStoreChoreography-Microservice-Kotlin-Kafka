package com.dyurekdeler.OnlineMovieStoreCustomer.repository

import com.dyurekdeler.OnlineMovieStoreCustomer.entity.Customer
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository: MongoRepository<Customer, String> {
}