package com.agotakiss.androidtest.domain.models

import java.io.Serializable

data class Movie(val posterPath: String? = null,
                 val isAdult: Boolean = false,
                 val overview: String? = null,
                 val releaseDateText: String? = null,
                 val genres: List<Genre>? = null,
                 val id: Int = 0,
                 val originalTitle: String? = null,
                 val originalLanguage: String? = null,
                 val title: String? = null,
                 val backdropPath: String? = null,
                 val popularity: Float = 0.toFloat(),
                 val voteCount: Int = 0,
                 val isVideo: Boolean = false,
                 val averageVote: Float = 0.toFloat()
):Serializable