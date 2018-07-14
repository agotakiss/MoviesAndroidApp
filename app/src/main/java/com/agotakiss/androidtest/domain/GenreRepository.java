package com.agotakiss.androidtest.domain;

import com.agotakiss.androidtest.data.models.Genre;

import java.util.List;

import io.reactivex.Single;

public interface GenreRepository {
    Single<List<Genre>> getGenres();

}
