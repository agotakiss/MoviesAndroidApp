package com.agotakiss.androidtest.data.store;

import com.agotakiss.androidtest.data.models.Genre;

import java.util.List;
import java.util.NoSuchElementException;

import io.reactivex.Completable;
import io.reactivex.Single;

public class GenreStoreImpl implements GenreStore {

    private List<Genre> genreList;

    @Override
    public Single<Boolean> hasData() {
        return Single.just(genreList != null && !genreList.isEmpty());
    }

    @Override
    public Single<List<Genre>> getGenreList() {
        if (genreList != null && !genreList.isEmpty()) {
            return Single.just(genreList);
        } else {
            return Single.error(new NoSuchElementException());
        }
    }

    @Override
    public Completable saveGenres(List<Genre> genreList) {
        return Completable.fromAction(() -> {
            GenreStoreImpl.this.genreList = genreList;
        });
    }
}
