package com.agotakiss.androidtest.data.repository;

import com.agotakiss.androidtest.data.models.Genre;
import com.agotakiss.androidtest.data.models.LoadGenresResponse;
import com.agotakiss.androidtest.data.network.MovieDbApi;
import com.agotakiss.androidtest.data.store.GenreStore;
import com.agotakiss.androidtest.domain.GenreRepository;
import com.agotakiss.androidtest.injector.Injector;

import java.util.List;

import io.reactivex.Single;

public class GenreRepositoryImpl implements GenreRepository {

    private MovieDbApi movieDbApi = Injector.getMovieDbApi();
    private GenreStore genreStore = Injector.getGenreStore();

    @Override
    public Single<List<Genre>> getGenres() {
        return genreStore.hasData().flatMap(hasData -> {
            if (hasData) {
                return genreStore.getGenreList();
            } else {
                return movieDbApi.getGenres()
                        .map(LoadGenresResponse::getGenres)
                        .doOnSuccess(genreList -> genreStore.saveGenres(genreList).blockingAwait());
            }
        });

    }

}
