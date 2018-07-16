package com.agotakiss.androidtest.data.store;


import com.agotakiss.androidtest.data.models.GenreDataModel;
import com.agotakiss.androidtest.domain.models.Genre;

import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface GenreStore {

    Single<Boolean> hasData();

    Single<Map<Integer, GenreDataModel>> getGenreMap();

    Completable saveGenres(Map<Integer, GenreDataModel> genreMap);
}
