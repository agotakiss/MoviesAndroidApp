package com.agotakiss.androidtest.domain.models

import com.google.gson.annotations.SerializedName

data class Actor (
    val birthday: String? = null,
    val knownForDepartment: String? = null,
    val id: Int,
    val name: String? = null,
    val biography: String? = null,
    val popularity: Double? = 0.0,
    val placeOfBirth: String? = null,
    val profilePath: String? = null
)