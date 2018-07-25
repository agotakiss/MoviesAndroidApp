package com.agotakiss.androidtest.data.repository

import android.util.Log
import com.agotakiss.androidtest.data.mapper.toCast
import com.agotakiss.androidtest.di.Injector
import com.agotakiss.androidtest.domain.models.Cast
import com.agotakiss.androidtest.domain.repository.CastRepository
import io.reactivex.Single

class CastRepositoryImpl : CastRepository {

    private val movieDbApi = Injector.getMovieDbApi()

    override fun getCast(movieId: Int): Single<List<Cast>> {
        return movieDbApi.getCredits(movieId)
            .map { it.cast }
            .toObservable()
            .flatMapIterable { it }
            .map { it.toCast() }
            .toList()
    }
}
