package com.dyurekdeler.OnlineMovieStoreInventory.service

import com.dyurekdeler.OnlineMovieStoreInventory.entity.Movie
import com.dyurekdeler.OnlineMovieStoreInventory.repository.MovieRepository
import com.dyurekdeler.OnlineMovieStoreInventory.request.MovieRequest
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.lang.Exception
import java.time.LocalDateTime

@Service
class MovieService(
    private val movieRepository: MovieRepository
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    fun findById(id: String): Movie {
        return movieRepository.findById(id)
            .orElseThrow{ Exception("Movie with $id is not found") }
    }

    fun createMovie(request: MovieRequest): Movie {
        val movie = movieRepository.save(
            Movie(
                title = request.title,
                duration = request.duration,
                about = request.about,
                quantity = request.quantity
            )
        )
        return movie
    }

    fun updateMovie(id: String, request: MovieRequest): Movie {
        val movieToUpdate = findById(id)
        val updatedMovie = movieRepository.save(
            movieToUpdate.apply {
                title = request.title
                duration = request.duration
                about = request.about
                quantity = request.quantity
            }
        )
        return updatedMovie
    }

    fun deleteById(id:String) {
        val movieToDelete = findById(id)
        movieRepository.delete(movieToDelete)
    }
}