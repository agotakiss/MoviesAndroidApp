package com.agotakiss.androidtest.domain.repository;

import com.agotakiss.androidtest.domain.models.Genre;

import java.util.Map;

import io.reactivex.Single;

public interface GenreRepository {
    Single<Map<Integer, Genre>> getGenres();

}
