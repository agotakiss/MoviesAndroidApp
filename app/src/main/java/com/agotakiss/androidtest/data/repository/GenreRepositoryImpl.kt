package com.agotakiss.androidtest.data.repository

import com.agotakiss.androidtest.data.mapper.toGenre
import com.agotakiss.androidtest.domain.models.Genre
import com.agotakiss.androidtest.domain.repository.GenreRepository
import com.agotakiss.androidtest.di.Injector
import io.reactivex.Single

class GenreRepositoryImpl : GenreRepository {

    private val movieDbApi = Injector.getMovieDbApi()
    private val genreStore = Injector.getGenreStore()

    override fun getGenres(): Single<Map<Int, Genre>> {
        return genreStore.hasData().flatMap { hasData ->
            if (hasData) {
                genreStore.getGenreMap()

            } else {
                getGenresFromApi()
                        .doOnSuccess { genreStore.saveGenres(it).blockingAwait() }
            }
        }
    }

    private fun getGenresFromApi(): Single<Map<Int, Genre>> = movieDbApi.getGenres()
            .map { it.genres }
            .toObservable()
            .flatMapIterable { it }
            .map { it.toGenre() }
            .toMap { it.id }
}
