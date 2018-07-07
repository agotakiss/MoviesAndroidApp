package com.agotakiss.androidtest.network;

import com.agotakiss.androidtest.models.LoadMoviesResponse;

import retrofit2.Callback;
import retrofit2.Retrofit;

public class MovieDbManager {
    //You can use this base url to load images with 342 pixel width


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

    public void loadPopularMovies(int page, Callback<LoadMoviesResponse> callback) {
        movieDbApi.getPopularMovies(page).enqueue(callback);

    }
    public void loadSimilarMovies(String movieId, int page, Callback<LoadMoviesResponse> callback) {
        movieDbApi.getSimilarMovies(movieId, page).enqueue(callback);
    }
}
