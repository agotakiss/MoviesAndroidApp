package com.agotakiss.movie4u.domain.models

import java.io.Serializable

data class Movie(
    var posterPath: String? = null,
    var isAdult: Boolean = false,
    var overview: String? = null,
    var releaseDateText: String? = null,
    var genres: List<Genre>? = null,
    var id: Int = 0,
    var originalTitle: String? = null,
    var originalLanguage: String? = null,
    var title: String? = null,
    var backdropPath: String? = null,
    var popularity: Float = 0.toFloat(),
    var voteCount: Int = 0,
    var isVideo: Boolean = false,
    var averageVote: Float = 0.toFloat(),
    var isFavorite: Boolean = false
) : Serializable