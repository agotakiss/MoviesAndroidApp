package com.agotakiss.androidtest.data.models

import com.google.gson.annotations.SerializedName

data class CastApiModel(
    @SerializedName("cast_id")
    val castId: Int = 0,

    @SerializedName("character")
    val character: String? = null,

    @SerializedName("credit_id")
    val creditId: String? = null,

    @SerializedName("gender")
    val gender: Int = 0,

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("order")
    val order: Int = 0,

    @SerializedName("profile_path")
    val profilePath: String? = null
)