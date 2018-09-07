package com.agotakiss.movie4u.domain.repository

import com.agotakiss.movie4u.domain.models.Actor
import io.reactivex.Single

interface ActorRepository {
    fun getActor(actorId: Int): Single<Actor>
}