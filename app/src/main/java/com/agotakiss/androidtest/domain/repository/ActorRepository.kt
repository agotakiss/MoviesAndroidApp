package com.agotakiss.androidtest.domain.repository

import com.agotakiss.androidtest.domain.models.Actor
import io.reactivex.Single

interface ActorRepository {
    fun getActor(actorId: Int): Single<Actor>

}