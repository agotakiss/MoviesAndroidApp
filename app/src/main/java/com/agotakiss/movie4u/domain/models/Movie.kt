package com.agotakiss.movie4u.domain.models

import java.io.Serializable

// @Entity(tableName = "favorite_movies")
data class Movie(
//    @ColumnInfo(name = "poster_path")
    var posterPath: String? = null,
    var isAdult: Boolean = false,
//    @ColumnInfo(name = "overview")
    var overview: String? = null,
//    @ColumnInfo(name = "release_date")
    var releaseDateText: String? = null,
//    @ColumnInfo(name = "genres")
    var genres: List<Genre>? = null,
//    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,
    var originalTitle: String? = null,
    var originalLanguage: String? = null,
//    @ColumnInfo(name = "title")
    var title: String? = null,
//    @ColumnInfo(name = "backdrop_path")
    var backdropPath: String? = null,
    var popularity: Float = 0.toFloat(),
    var voteCount: Int = 0,
    var isVideo: Boolean = false,
//    @ColumnInfo(name = "average_vote")
    var averageVote: Float = 0.toFloat(),
//    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false
) : Serializable