package com.dyurekdeler.OnlineMovieStoreInventory.controller

import com.dyurekdeler.OnlineMovieStoreInventory.entity.Movie
import com.dyurekdeler.OnlineMovieStoreInventory.request.MovieRequest
import com.dyurekdeler.OnlineMovieStoreInventory.service.MovieService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class MovieController(
    private val movieService: MovieService
) {

    @GetMapping("/{id}")
    fun getMovie(@PathVariable("id") id: String): Movie {
        return movieService.findById(id)
    }

    @PostMapping
    fun createMovie(@RequestBody request: MovieRequest): ResponseEntity<Movie> {
        val movie = movieService.createMovie(request)
        return ResponseEntity(movie, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateMovie(@RequestBody request: MovieRequest, @PathVariable("id") id: String): ResponseEntity<Movie> {
        val updatedMovie = movieService.updateMovie(id, request)
        return ResponseEntity.ok(updatedMovie)
    }

    @DeleteMapping("/{id}")
    fun deleteMovie(@PathVariable("id") id: String) {
        movieService.deleteById(id)
    }

}