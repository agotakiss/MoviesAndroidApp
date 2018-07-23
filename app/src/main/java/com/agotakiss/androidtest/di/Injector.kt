package com.agotakiss.androidtest.di

import android.app.Activity


class Injector private constructor() {

    companion object {
        fun inject(activity: Activity) {
            ActivityInjector.get(activity).inject(activity)
        }
        fun clearComponent(activity: Activity){
            ActivityInjector.get(activity).clear(activity)
        }
        fun inject(controller: Controller){

        }
        fun clearComponent(controller)
    }
}


//object Injector {
//
//    private var retrofit: Retrofit? = null
//
//    private var movieDbApi: MovieDbApi? = null
//
//    private var genreRepository: GenreRepository? = null
//
//    private var movieRepository: MovieRepository? = null
//
//    private var genreStore: GenreStore? = null
//
//    fun getRetrofit(): Retrofit {
//        if (retrofit == null) {
//            retrofit = RetrofitHelper.initRetrofit()
//        }
//        return retrofit!!
//    }
//
//    fun getMovieDbApi(): MovieDbApi {
//        if (movieDbApi == null) {
//            movieDbApi = getRetrofit().create(MovieDbApi::class.java)
//        }
//        return movieDbApi!!
//    }
//
//    fun getGenreRepository(): GenreRepository {
//        if (genreRepository == null) {
//            genreRepository = GenreRepositoryImpl()
//        }
//        return genreRepository!!
//    }
//
//    fun getMovieRepository(): MovieRepository {
//        if (movieRepository == null) {
//            movieRepository = MovieRepositoryImpl()
//        }
//        return movieRepository!!
//    }
//
//    fun getGenreStore(): GenreStore {
//        if (genreStore == null) {
//            genreStore = GenreStoreImpl()
//        }
//        return genreStore!!
//    }
//}
