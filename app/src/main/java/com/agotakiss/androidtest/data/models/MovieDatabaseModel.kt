package com.agotakiss.androidtest.data.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class MovieDatabaseModel(
    @ColumnInfo(name = "poster_path")
    var posterPath: String? = null,
    var isAdult: Boolean = false,
    @ColumnInfo(name = "overview")
    var overview: String? = null,
    @ColumnInfo(name = "release_date")
    var releaseDateText: String? = null,
    @ColumnInfo(name = "genres")
    var genres: List<Int>? = null,
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,
    var originalTitle: String? = null,
    var originalLanguage: String? = null,
    @ColumnInfo(name = "title")
    var title: String? = null,
    @ColumnInfo(name = "backdrop_path")
    var backdropPath: String? = null,
    var popularity: Float = 0.toFloat(),
    var voteCount: Int = 0,
    var isVideo: Boolean = false,
    @ColumnInfo(name = "average_vote")
    var averageVote: Float = 0.toFloat(),
    @ColumnInfo(name = "wether_is_favorite")
    var isFavorite: Boolean = false,
    @ColumnInfo(name = "addedOn")
    var addedOn: Long
)