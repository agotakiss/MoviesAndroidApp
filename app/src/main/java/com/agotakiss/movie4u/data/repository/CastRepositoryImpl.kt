package com.agotakiss.movie4u.data.repository

import com.agotakiss.movie4u.data.mapper.toCast
import com.agotakiss.movie4u.data.network.MovieDbApi
import com.agotakiss.movie4u.domain.models.Cast
import com.agotakiss.movie4u.domain.repository.CastRepository
import io.reactivex.Single
import javax.inject.Inject

class CastRepositoryImpl @Inject constructor(
    private val movieDbApi: MovieDbApi
) : CastRepository {

    override fun getCast(movieId: Int): Single<List<Cast>> {
        return movieDbApi.getCredits(movieId)
            .map { it.cast }
            .toObservable()
            .flatMapIterable { it }
            .map { it.toCast() }
            .toList()
    }
}
