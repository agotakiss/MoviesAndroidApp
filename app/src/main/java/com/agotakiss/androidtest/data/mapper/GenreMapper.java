package com.agotakiss.androidtest.data.mapper;

import com.agotakiss.androidtest.data.models.GenreDataModel;
import com.agotakiss.androidtest.domain.models.Genre;

public class GenreMapper {
    public static Genre transform(GenreDataModel genreApiModel){
        Genre genre = new Genre(genreApiModel.getId(), genreApiModel.getName());
        return genre;
    }
}
