package com.agotakiss.movie4u.data.mapper

import com.agotakiss.movie4u.data.models.CastApiModel
import com.agotakiss.movie4u.domain.models.Cast

fun CastApiModel.toCast() = Cast(castId, character, creditId, gender, id, name, order, profilePath)