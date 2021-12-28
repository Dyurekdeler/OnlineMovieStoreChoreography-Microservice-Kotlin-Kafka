package com.dyurekdeler.OnlineMovieStoreInventory.repository

import com.dyurekdeler.OnlineMovieStoreInventory.entity.Movie
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface MovieRepository: MongoRepository<Movie, String> {
}