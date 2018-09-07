package com.agotakiss.movie4u.data.models

import com.google.gson.annotations.SerializedName

data class GenreApiModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
