package com.agotakiss.movie4u.data.mapper

import com.agotakiss.movie4u.data.models.MovieApiModel
import com.agotakiss.movie4u.data.models.MovieDatabaseModel
import com.agotakiss.movie4u.domain.models.Genre
import com.agotakiss.movie4u.domain.models.Movie

fun MovieApiModel.toMovie(genreMap: Map<Int, Genre>): Movie =

    Movie(posterPath,
        isAdult,
        overview,
        releaseDateText,
        genreIdList?.mapNotNull { genreMap[it] },
        id,
        originalTitle,
        originalLanguage,
        title,
        backdropPath,
        popularity,
        voteCount,
        isVideo,
        averageVote)

fun MovieDatabaseModel.toMovie(genreMap: Map<Int, Genre>): Movie =

    Movie(posterPath,
        isAdult,
        overview,
        releaseDateText,
        genres?.mapNotNull { genreMap[it] },
        id,
        originalTitle,
        originalLanguage,
        title,
        backdropPath,
        popularity,
        voteCount,
        isVideo,
        averageVote)
