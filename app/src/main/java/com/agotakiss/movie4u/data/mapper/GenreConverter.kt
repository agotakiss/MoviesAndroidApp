package com.agotakiss.movie4u.data.mapper

import android.arch.persistence.room.TypeConverter

class GenreConverter {

    @TypeConverter
    fun genreListToString(genreIdList: List<Int>): String = genreIdList.joinToString()

    @TypeConverter
    fun stringToGenreIdList(genreIdListString: String): List<Int> {
        return if (genreIdListString != "") {
            genreIdListString.split(",")
                .map { it.trim() }
                .map { it.toInt() }
        } else emptyList()
    }
}