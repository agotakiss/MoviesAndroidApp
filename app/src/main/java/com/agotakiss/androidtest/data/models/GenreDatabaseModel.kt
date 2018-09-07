package com.agotakiss.androidtest.data.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "genres_table")
data class GenreDatabaseModel(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo(name = "genreName")
    val name: String
)