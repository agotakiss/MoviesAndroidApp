package com.agotakiss.androidtest.network;
import com.agotakiss.androidtest.models.Genre;
import com.agotakiss.androidtest.models.LoadGenresResponse;
import com.agotakiss.androidtest.models.LoadMoviesResponse;
import com.agotakiss.androidtest.models.Movie;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class MovieDbManager {
        //region singleton
    private static MovieDbManager instance;

    public static MovieDbManager getInstance() {
        if(instance == null) {
            instance = new MovieDbManager();
        }
        return instance;
    }
    //endregion

    private Retrofit retrofit;
    private MovieDbApi movieDbApi;

    private MovieDbManager(){
        retrofit = RetrofitHelper.initRetrofit();
        movieDbApi = retrofit.create(MovieDbApi.class);
    }
//    public void loadPopularMovies(int page, com.agotakiss.androidtest.network.Callback<LoadMoviesResponse> callback){
//        movieDbApi.getPopularMovies(page).enqueue(callback);
//    }

//    public void loadPopularMovies(int page, Callback<LoadMoviesResponse> callback) {
//        movieDbApi.getPopularMovies(page).enqueue(callback);
//    }
//    public void loadSimilarMovies(String movieId, int page, Callback<LoadMoviesResponse> callback) {
//        movieDbApi.getSimilarMovies(movieId, page).enqueue(callback);
//    }
//    public void loadGenres(Callback<LoadGenresResponse> callback) {
//        movieDbApi.getGenres().enqueue(callback);
//    }

    public Single<List<Movie>> loadPopularMovies(int page) {
        return movieDbApi.getPopularMovies(page)
                .map(LoadMoviesResponse::getMovies);
    }

    public Single<List<Movie>> loadSimilarMovies(String movieId, int page) {
        return movieDbApi.getSimilarMovies(movieId, page)
                .map(LoadMoviesResponse::getMovies);
    }

    public Single<List<Genre>> loadGenres() {
       return movieDbApi.getGenres()
               .map(LoadGenresResponse::getGenres);
    }
}
