package com.agotakiss.androidtest.data.models

import com.google.gson.annotations.SerializedName

import java.io.Serializable

class LoadGenresResponse : Serializable {

    @SerializedName("genres")
    val genres: List<GenreApiModel>? = null
}
