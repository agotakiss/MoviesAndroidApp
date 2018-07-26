package com.agotakiss.androidtest.data.repository

import com.agotakiss.androidtest.data.mapper.toActor
import com.agotakiss.androidtest.di.Injector
import com.agotakiss.androidtest.domain.models.Actor
import com.agotakiss.androidtest.domain.repository.ActorRepository
import io.reactivex.Single

class ActorRepositoryImpl : ActorRepository {

    private val movieDbApi = Injector.getMovieDbApi()

    override fun getActor(actorId: Int): Single<Actor> {
        return movieDbApi.getActor(actorId)
            .map { it.toActor() }
    }
}