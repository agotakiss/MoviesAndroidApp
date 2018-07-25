package com.agotakiss.androidtest.domain.models

import java.io.Serializable

data class Cast(val castId: Int = 0,
    val character: String? = null,
    val creditId: String? = null,
    val gender: Int = 0,
    val id: Int,
    val name: String? = null,
    val order: Int = 0,
    val profilePath: String? = null) : Serializable