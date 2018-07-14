package com.agotakiss.androidtest.data.store;

import com.agotakiss.androidtest.data.models.Genre;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface GenreStore {

    Single<Boolean> hasData();

    Single<List<Genre>> getGenreList();

    Completable saveGenres(List<Genre> genreList);
}
