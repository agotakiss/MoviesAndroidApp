package com.agotakiss.androidtest.data.mapper

import com.agotakiss.androidtest.data.models.CastApiModel
import com.agotakiss.androidtest.domain.models.Cast

fun CastApiModel.toCast() = Cast(castId, character, creditId, gender, id, name, order, profilePath)