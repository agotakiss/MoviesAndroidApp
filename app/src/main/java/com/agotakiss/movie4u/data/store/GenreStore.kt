package com.agotakiss.movie4u.data.store

import com.agotakiss.movie4u.domain.models.Genre
import io.reactivex.Completable
import io.reactivex.Single

interface GenreStore {

    fun getGenreMap(): Single<Map<Int, Genre>>

    fun hasData(): Single<Boolean>

    fun saveGenres(genreMap: Map<Int, Genre>): Completable
}
