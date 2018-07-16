package com.agotakiss.androidtest.data.mapper;

import com.agotakiss.androidtest.data.models.MovieDataModel;
import com.agotakiss.androidtest.domain.models.Genre;
import com.agotakiss.androidtest.domain.models.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MovieMapper {
    public static Movie transform(MovieDataModel movieApiModel, Map<Integer, Genre> genreMap) {
        List<Genre> genreList = new ArrayList<>();
        List<Integer> genreIdList = movieApiModel.getGenreIdList();
        for(int i = 0; i <genreIdList.size(); i++){
           genreList.add(genreMap.get(genreIdList.get(i)));
        }
        Movie movie = new Movie(movieApiModel.getPosterPath(),
                movieApiModel.isAdult(),
                movieApiModel.getOverview(),
                movieApiModel.getReleaseDateText(),
                genreList,
                movieApiModel.getId(),
                movieApiModel.getOriginalTitle(),
                movieApiModel.getOriginalLanguage(),
                movieApiModel.getTitle(),
                movieApiModel.getBackdropPath(),
                movieApiModel.getPopularity(),
                movieApiModel.getVoteCount(),
                movieApiModel.isVideo(),
                movieApiModel.getAverageVote());
        return movie;

    }
}
