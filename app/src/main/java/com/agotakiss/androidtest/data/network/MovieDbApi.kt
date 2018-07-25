package com.agotakiss.androidtest.data.network

import com.agotakiss.androidtest.data.models.ActorApiModel
import com.agotakiss.androidtest.data.models.LoadCreditResponse
import com.agotakiss.androidtest.data.models.LoadGenresResponse
import com.agotakiss.androidtest.data.models.LoadMoviesResponse

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDbApi {

    @GET("genre/movie/list")
    fun getGenres(): Single<LoadGenresResponse>

    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int): Single<LoadMoviesResponse>

    @GET("movie/{movie_id}/similar")
    fun getSimilarMovies(@Path(value = "movie_id", encoded = true) movieId: Int, @Query("page") page: Int): Single<LoadMoviesResponse>

    @GET("movie/{movie_id}/credits")
    fun getCredits(@Path(value = "movie_id", encoded = true) movieId: Int): Single<LoadCreditResponse>

    @GET("person/{person_id}")
    fun getActor(@Path(value = "person_id", encoded = true) personId: Int): Single<ActorApiModel>
}
