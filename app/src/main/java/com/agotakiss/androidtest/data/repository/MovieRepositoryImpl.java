package com.agotakiss.androidtest.data.repository;

import com.agotakiss.androidtest.data.models.LoadMoviesResponse;
import com.agotakiss.androidtest.data.models.Movie;
import com.agotakiss.androidtest.data.network.MovieDbApi;
import com.agotakiss.androidtest.domain.MovieRepository;
import com.agotakiss.androidtest.injector.Injector;

import java.util.List;

import io.reactivex.Single;

public class MovieRepositoryImpl implements MovieRepository{

    private MovieDbApi movieDbApi = Injector.getMovieDbApi();

    @Override
    public Single<List<Movie>> getPopularMovies(int page) {
        return movieDbApi.getPopularMovies(page)
                .map(LoadMoviesResponse::getMovies);
    }

    @Override
    public Single<List<Movie>> getSimilarMovies(String movieId, int page) {
        return movieDbApi.getSimilarMovies(movieId, page)
                .map(LoadMoviesResponse::getMovies);
    }

}
