package com.agotakiss.androidtest.network;

import com.agotakiss.androidtest.models.LoadMoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDbApi {

    @GET("movie/popular")
    Call<LoadMoviesResponse> getPopularMovies(@Query("page") int page);

    @GET("movie/{movie_id}/similar")
    Call<LoadMoviesResponse> getSimilarMovies(@Path(value = "movie_id", encoded = true) String movieId, @Query("page") int page);
}
