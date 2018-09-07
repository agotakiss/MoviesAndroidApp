package com.agotakiss.androidtest.data.store

import com.agotakiss.androidtest.domain.models.Genre
import java.util.NoSuchElementException

import io.reactivex.Completable
import io.reactivex.Single

class GenreStoreImpl : GenreStore {

    private var genreMap: Map<Int, Genre>? = null

    override fun hasData(): Single<Boolean> {
        return Single.just(genreMap != null && !genreMap!!.isEmpty())
    }

    override fun getGenreMap(): Single<Map<Int, Genre>> {
        return if (genreMap != null && !genreMap!!.isEmpty()) {
            Single.just(genreMap)
        } else {
            Single.error(NoSuchElementException())
        }
    }

    override fun saveGenres(genreMap: Map<Int, Genre>): Completable {
        this.genreMap = genreMap
        return Completable.fromAction { this.genreMap }
    }
}
