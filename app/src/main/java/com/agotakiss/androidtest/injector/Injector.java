package com.agotakiss.androidtest.injector;

import com.agotakiss.androidtest.data.network.MovieDbApi;
import com.agotakiss.androidtest.data.network.RetrofitHelper;
import com.agotakiss.androidtest.data.repository.GenreRepositoryImpl;
import com.agotakiss.androidtest.data.repository.MovieRepositoryImpl;
import com.agotakiss.androidtest.data.store.GenreStore;
import com.agotakiss.androidtest.data.store.GenreStoreImpl;
import com.agotakiss.androidtest.domain.repository.GenreRepository;
import com.agotakiss.androidtest.domain.repository.MovieRepository;

import retrofit2.Retrofit;

public class Injector {

    private static Retrofit retrofit;

    public static Retrofit getRetrofit(){
        if(retrofit == null){
            retrofit = RetrofitHelper.initRetrofit();
        }
        return retrofit;
    }

    private static MovieDbApi movieDbApi;

    public static MovieDbApi getMovieDbApi(){
        if(movieDbApi == null){
            movieDbApi = getRetrofit().create(MovieDbApi.class);
        }
        return movieDbApi;
    }

    private static GenreRepository genreRepository;

    public static GenreRepository getGenreRepository(){
        if(genreRepository == null){
            genreRepository = new GenreRepositoryImpl();
        }
        return genreRepository;
    }

    private static MovieRepository movieRepository;

    public static MovieRepository getMovieRepository(){
        if(movieRepository == null){
            movieRepository = new MovieRepositoryImpl();
        }
        return movieRepository;
    }

    private static GenreStore genreStore;

    public static GenreStore getGenreStore(){
        if(genreStore == null){
            genreStore = new GenreStoreImpl();
        }
        return genreStore;
    }


}
