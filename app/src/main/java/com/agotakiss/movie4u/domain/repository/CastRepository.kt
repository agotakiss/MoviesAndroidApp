package com.agotakiss.movie4u.domain.repository

import com.agotakiss.movie4u.domain.models.Cast
import io.reactivex.Single

interface CastRepository {
    fun getCast(movieId: Int): Single<List<Cast>>
}