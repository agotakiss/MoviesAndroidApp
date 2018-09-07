package com.agotakiss.movie4u.data.repository

import com.agotakiss.movie4u.data.mapper.toGenre
import com.agotakiss.movie4u.data.network.MovieDbApi
import com.agotakiss.movie4u.data.store.GenreStore
import com.agotakiss.movie4u.domain.models.Genre
import com.agotakiss.movie4u.domain.repository.GenreRepository
import io.reactivex.Single
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
    private val genreStore: GenreStore,
    private val movieDbApi: MovieDbApi
) : GenreRepository {

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
