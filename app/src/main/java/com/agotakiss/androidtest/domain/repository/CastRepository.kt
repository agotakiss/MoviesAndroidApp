package com.agotakiss.androidtest.domain.repository

import com.agotakiss.androidtest.domain.models.Cast
import com.agotakiss.androidtest.domain.models.Movie
import io.reactivex.Single

interface CastRepository {
    fun getCast(movieId: Int): Single<List<Cast>>
}