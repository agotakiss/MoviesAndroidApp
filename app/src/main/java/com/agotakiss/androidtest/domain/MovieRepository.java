package com.agotakiss.androidtest.domain;

import com.agotakiss.androidtest.data.models.Movie;

import java.util.List;

import io.reactivex.Single;

public interface MovieRepository {

    Single<List<Movie>> getPopularMovies(int page);

    Single<List<Movie>> getSimilarMovies(String movieId, int page);
}
