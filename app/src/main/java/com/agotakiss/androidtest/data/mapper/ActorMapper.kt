package com.agotakiss.androidtest.data.mapper

import com.agotakiss.androidtest.data.models.ActorApiModel
import com.agotakiss.androidtest.domain.models.Actor

fun ActorApiModel.toActor() = Actor(birthday, knownForDepartment, id, name, biography, popularity, placeOfBirth, profilePath)