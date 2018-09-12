package com.agotakiss.movie4u.data.repository

import com.agotakiss.movie4u.data.mapper.toActor
import com.agotakiss.movie4u.data.network.MovieDbApi
import com.agotakiss.movie4u.domain.models.Actor
import com.agotakiss.movie4u.domain.repository.ActorRepository
import io.reactivex.Single
import javax.inject.Inject

class ActorRepositoryImpl @Inject constructor(
    private val movieDbApi: MovieDbApi
) : ActorRepository {

    override fun getActor(actorId: Int): Single<Actor> =
        movieDbApi.getActor(actorId)
            .map { it.toActor() }
}