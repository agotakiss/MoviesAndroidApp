package com.agotakiss.movie4u.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class LoadActorsMoviesResponse : Serializable {

    @SerializedName("cast")
    val cast: List<MovieApiModel>? = null
}