package com.agotakiss.movie4u.domain.repository

import com.agotakiss.movie4u.domain.models.Genre

import io.reactivex.Single

interface GenreRepository {
    fun getGenres(): Single<Map<Int, Genre>>
}
