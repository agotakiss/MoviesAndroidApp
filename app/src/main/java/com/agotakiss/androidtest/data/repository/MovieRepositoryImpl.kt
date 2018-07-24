package com.agotakiss.androidtest.data.repository

import com.agotakiss.androidtest.data.mapper.toMovie
import com.agotakiss.androidtest.data.models.LoadMoviesResponse
import com.agotakiss.androidtest.data.models.MovieApiModel
import com.agotakiss.androidtest.di.Injector
import com.agotakiss.androidtest.domain.models.Movie
import com.agotakiss.androidtest.domain.repository.MovieRepository
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.SingleOnSubscribe
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepositoryImpl : MovieRepository {

    private val movieDbApi = Injector.getMovieDbApi()
    private val genreRepository = Injector.getGenreRepository()

//    override fun getPopularMovies(page: Int): Single<List<Movie>> {
//        return movieDbApi.getPopularMovies(page)
//                .map<List<MovieApiModel>> { it.movies }
//                .toObservable()
//                .flatMapIterable<MovieApiModel> { it }
//                .map { it.toMovie(genreRepository.getGenres().blockingGet()) }
//                .toList()
//    }

    override fun getPopularMovies(page: Int): Single<List<Movie>> {
        return Single.create(object : SingleOnSubscribe<LoadMoviesResponse> {
            override fun subscribe(emitter: SingleEmitter<LoadMoviesResponse>) {
                val response = movieDbApi.getPopularMoviesCall(page).execute()
                if(response.isSuccessful) {
                    emitter.onSuccess(response.body()!!)
                } else {
                    emitter.onError(Throwable(response.errorBody()!!.toString()))
                }

                movieDbApi.getPopularMoviesCall(page).enqueue(object : Callback<LoadMoviesResponse> {
                    override fun onFailure(call: Call<LoadMoviesResponse>?, t: Throwable?) {
                        emitter.onError(t!!)
                    }

                    override fun onResponse(call: Call<LoadMoviesResponse>?, response: Response<LoadMoviesResponse>?) {
                        emitter.onSuccess(response!!.body()!!)
                    }

                })

            }

        }).subscribeOn(Schedulers.io())


//        {emitter ->
//            movieDbApi.getPopularMoviesCall(page).enqueue(object: Callback<LoadMoviesResponse> {
//                override fun onFailure(call: Call<LoadMoviesResponse>?, t: Throwable?) {
//                    emitter.onError(t!!)
//
//                }
//
//                override fun onResponse(call: Call<LoadMoviesResponse>?, response: Response<LoadMoviesResponse>?) {
//                    emitter.onSuccess(response!!.body()!! as LoadMoviesResponse)
//                }
//
//            })
//        }

            .map<List<MovieApiModel>> { it.movies }
            .toObservable()
            .flatMapIterable<MovieApiModel> { it }
            .map { it.toMovie(genreRepository.getGenres().blockingGet()) }
            .toList()
    }

    override fun getSimilarMovies(movieId: Int, page: Int): Single<List<Movie>> {
        return movieDbApi.getSimilarMovies(movieId, page)
            .map<List<MovieApiModel>> { it.movies }
            .toObservable()
            .flatMapIterable<MovieApiModel> { it }
            .map { it.toMovie(genreRepository.getGenres().blockingGet()) }
            .toList()
    }
}
