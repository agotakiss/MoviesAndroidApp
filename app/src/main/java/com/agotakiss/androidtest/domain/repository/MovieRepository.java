package com.agotakiss.androidtest.domain.repository;

import com.agotakiss.androidtest.domain.models.Movie;

import java.util.List;

import io.reactivex.Single;

public interface MovieRepository {

    Single<List<Movie>> getPopularMovies(int page);

    Single<List<Movie>> getSimilarMovies(String movieId, int page);
}
