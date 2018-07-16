package com.agotakiss.androidtest.data.repository;

import com.agotakiss.androidtest.data.mapper.GenreMapper;
import com.agotakiss.androidtest.data.models.GenreDataModel;
import com.agotakiss.androidtest.data.models.LoadGenresResponse;
import com.agotakiss.androidtest.data.network.MovieDbApi;
import com.agotakiss.androidtest.data.store.GenreStore;
import com.agotakiss.androidtest.domain.repository.GenreRepository;
import com.agotakiss.androidtest.domain.models.Genre;
import com.agotakiss.androidtest.injector.Injector;

import java.util.List;
import java.util.Map;

import io.reactivex.Single;

public class GenreRepositoryImpl implements GenreRepository {

    private MovieDbApi movieDbApi = Injector.getMovieDbApi();
    private GenreStore genreStore = Injector.getGenreStore();

    @Override
    public Single<Map<Integer, Genre>> getGenres() {
        return genreStore.hasData().flatMap(hasData -> {
            if (hasData) {
                return genreStore.getGenreMap()
                        .toObservable()
                        .flatMapIterable(genreMap -> genreMap.values())
                        .map(GenreMapper::transform)
                        .toMap(Genre::getId);
            } else {
                return movieDbApi.getGenres()
                        .map(LoadGenresResponse::getGenres)
                        .toObservable()
                        .flatMapIterable(genreApiModels -> genreApiModels)
                        .toMap(GenreDataModel::getId)
                        .doOnSuccess(genreMap -> genreStore.saveGenres(genreMap).blockingAwait())
                        .toObservable()
                        .flatMapIterable(genreMap -> genreMap.values())
                        .map(GenreMapper::transform)
                        .toMap(Genre::getId);
            }
        });

    }

}
