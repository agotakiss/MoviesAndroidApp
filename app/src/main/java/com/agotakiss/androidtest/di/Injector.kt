package com.agotakiss.androidtest.di

import com.agotakiss.androidtest.data.network.MovieDbApi
import com.agotakiss.androidtest.data.network.RetrofitHelper
import com.agotakiss.androidtest.data.repository.CastRepositoryImpl
import com.agotakiss.androidtest.data.repository.GenreRepositoryImpl
import com.agotakiss.androidtest.data.repository.MovieRepositoryImpl
import com.agotakiss.androidtest.data.store.GenreStore
import com.agotakiss.androidtest.data.store.GenreStoreImpl
import com.agotakiss.androidtest.domain.repository.CastRepository
import com.agotakiss.androidtest.domain.repository.GenreRepository
import com.agotakiss.androidtest.domain.repository.MovieRepository
import retrofit2.Retrofit


object Injector {

    private var retrofit: Retrofit? = null

    private var movieDbApi: MovieDbApi? = null

    private var genreRepository: GenreRepository? = null

    private var movieRepository: MovieRepository? = null

    private var castRepository : CastRepository? = null

    private var genreStore: GenreStore? = null

    fun getRetrofit(): Retrofit {
        if (retrofit == null) {
            retrofit = RetrofitHelper.initRetrofit()
        }
        return retrofit!!
    }

    fun getMovieDbApi(): MovieDbApi {
        if (movieDbApi == null) {
            movieDbApi = getRetrofit().create(MovieDbApi::class.java)
        }
        return movieDbApi!!
    }

    fun getGenreRepository(): GenreRepository {
        if (genreRepository == null) {
            genreRepository = GenreRepositoryImpl()
        }
        return genreRepository!!
    }

    fun getMovieRepository(): MovieRepository {
        if (movieRepository == null) {
            movieRepository = MovieRepositoryImpl()
        }
        return movieRepository!!
    }
    fun getCastRepository(): CastRepository {
        if (castRepository == null) {
            castRepository = CastRepositoryImpl()
        }
        return castRepository!!
    }

    fun getGenreStore(): GenreStore {
        if (genreStore == null) {
            genreStore = GenreStoreImpl()
        }
        return genreStore!!
    }
}
