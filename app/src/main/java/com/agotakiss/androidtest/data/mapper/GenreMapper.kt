package com.agotakiss.androidtest.data.mapper

import com.agotakiss.androidtest.data.models.GenreApiModel
import com.agotakiss.androidtest.domain.models.Genre

fun GenreApiModel.toGenre() = Genre(id, name)
