package com.agotakiss.androidtest.domain.repository

import com.agotakiss.androidtest.domain.models.Movie

import io.reactivex.Single

interface MovieRepository {

    fun getPopularMovies(page: Int): Single<List<Movie>>

    fun getSimilarMovies(movieId: Int, page: Int): Single<List<Movie>>

    fun getActorsMovies(actorId: Int): Single<List<Movie>>
}
