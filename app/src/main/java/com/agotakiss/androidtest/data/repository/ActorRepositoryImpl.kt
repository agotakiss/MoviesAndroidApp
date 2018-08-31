package com.agotakiss.androidtest.data.repository

import com.agotakiss.androidtest.data.mapper.toActor
import com.agotakiss.androidtest.data.network.MovieDbApi
import com.agotakiss.androidtest.domain.models.Actor
import com.agotakiss.androidtest.domain.repository.ActorRepository
import io.reactivex.Single
import javax.inject.Inject

class ActorRepositoryImpl @Inject constructor(
    private val movieDbApi: MovieDbApi
) : ActorRepository {

    override fun getActor(actorId: Int): Single<Actor> {
        return movieDbApi.getActor(actorId)
            .map { it.toActor() }
    }
}