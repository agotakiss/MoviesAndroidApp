package com.agotakiss.androidtest.data.network;

import com.agotakiss.androidtest.data.models.LoadGenresResponse;
import com.agotakiss.androidtest.data.models.LoadMoviesResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDbApi {

    @GET("movie/popular")
    Single<LoadMoviesResponse> getPopularMovies(@Query("page") int page);

    @GET("movie/{movie_id}/similar")
    Single<LoadMoviesResponse> getSimilarMovies(@Path(value = "movie_id", encoded = true) String movieId, @Query("page") int page);

    @GET("genre/movie/list")
    Single<LoadGenresResponse> getGenres();

}
