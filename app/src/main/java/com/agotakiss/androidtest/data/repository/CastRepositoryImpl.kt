package com.agotakiss.androidtest.data.repository

import com.agotakiss.androidtest.data.mapper.toCast
import com.agotakiss.androidtest.data.network.MovieDbApi
import com.agotakiss.androidtest.domain.models.Cast
import com.agotakiss.androidtest.domain.repository.CastRepository
import io.reactivex.Single
import javax.inject.Inject

class CastRepositoryImpl @Inject constructor(
   private val movieDbApi: MovieDbApi): CastRepository {

    override fun getCast(movieId: Int): Single<List<Cast>> {
        return movieDbApi.getCredits(movieId)
            .map { it.cast }
            .toObservable()
            .flatMapIterable { it }
            .map { it.toCast() }
            .toList()
    }
}
