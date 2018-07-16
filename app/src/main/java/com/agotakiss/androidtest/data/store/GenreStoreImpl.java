package com.agotakiss.androidtest.data.store;

import com.agotakiss.androidtest.data.models.GenreDataModel;
import com.agotakiss.androidtest.domain.models.Genre;

import java.util.Map;
import java.util.NoSuchElementException;

import io.reactivex.Completable;
import io.reactivex.Single;

public class GenreStoreImpl implements GenreStore {

    private Map<Integer, GenreDataModel> genreMap;

    @Override
    public Single<Boolean> hasData() {
        return Single.just(genreMap != null && !genreMap.isEmpty());
    }

    @Override
    public Single<Map<Integer, GenreDataModel>> getGenreMap() {
        if (genreMap != null && !genreMap.isEmpty()) {
            return Single.just(genreMap);
        } else {
            return Single.error(new NoSuchElementException());
        }
    }

    @Override
    public Completable saveGenres(Map<Integer, GenreDataModel> genreMap) {
        return Completable.fromAction(() -> {
            GenreStoreImpl.this.genreMap = this.genreMap;
        });
    }
}
