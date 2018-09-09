package com.agotakiss.movie4u.data.mapper

import com.agotakiss.movie4u.data.models.ActorApiModel
import com.agotakiss.movie4u.domain.models.Actor

fun ActorApiModel.toActor() =
    Actor(birthday, knownForDepartment, id, name, biography, popularity, placeOfBirth, profilePath)