package com.agotakiss.movie4u.data.mapper

import com.agotakiss.movie4u.data.models.GenreApiModel
import com.agotakiss.movie4u.domain.models.Genre

fun GenreApiModel.toGenre() = Genre(id, name)
