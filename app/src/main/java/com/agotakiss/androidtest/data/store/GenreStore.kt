package com.agotakiss.androidtest.data.store


import com.agotakiss.androidtest.domain.models.Genre
import io.reactivex.Completable
import io.reactivex.Single

interface GenreStore {

    fun getGenreMap(): Single<Map<Int, Genre>>

    fun hasData(): Single<Boolean>

    fun saveGenres(genreMap: Map<Int, Genre>): Completable
}
