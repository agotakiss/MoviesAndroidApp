package com.agotakiss.androidtest.data.models

import com.google.gson.annotations.SerializedName

data class CrewApiModel(

    @SerializedName("credit_id")
    val creditId: String? = null,

    @SerializedName("department")
    val department: String? = null,

    @SerializedName("gender")
    val gender: Int = 0,

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("job")
    val job: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("profile_path")
    val profilePath: String? = null
)
