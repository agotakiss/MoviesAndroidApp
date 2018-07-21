package com.agotakiss.androidtest.domain.repository

import com.agotakiss.androidtest.domain.models.Genre

import io.reactivex.Single

interface GenreRepository {
    fun getGenres(): Single<Map<Int, Genre>>
}
