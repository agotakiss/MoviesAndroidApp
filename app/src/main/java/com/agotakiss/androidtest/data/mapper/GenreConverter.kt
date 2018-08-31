package com.agotakiss.androidtest.data.mapper

import android.arch.persistence.room.TypeConverter

class GenreConverter() {

    @TypeConverter
    fun genreListToString(genreIdList: List<Int>): String = genreIdList.joinToString()

    @TypeConverter
    fun stringToGenreIdList(genreIdListString: String): List<Int> {
        return genreIdListString.split(",")
            .map { it.trim() }
            .map { it.toInt() }
    }
}

//@Inject
//private val genreRepository: GenreRepository


//    fun stringToGenreList(genreString: String): List<Genre> {
//        val genresMap = genreRepository.getGenres().blockingGet()
//        val genreStringList = genreString.split(",").map { it.trim() }
//        val genreList = mutableListOf<Genre>()
//        for (i in 0 until genreStringList.size) {
//            genreList.add(genresMap[i]!!)
//        }
//        return genreList
//    }