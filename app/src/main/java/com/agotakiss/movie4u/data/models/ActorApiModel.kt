package com.agotakiss.movie4u.data.models

import com.google.gson.annotations.SerializedName

data class ActorApiModel(
    @SerializedName("birthday")
    val birthday: String? = null,

    @SerializedName("known_for_department")
    val knownForDepartment: String? = null,

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("biography")
    val biography: String? = null,

    @SerializedName("popularity")
    val popularity: Double? = 0.0,

    @SerializedName("place_of_birth")
    val placeOfBirth: String? = null,

    @SerializedName("profile_path")
    val profilePath: String? = null
)