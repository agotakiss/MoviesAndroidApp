package com.agotakiss.movie4u.data.repository

import com.agotakiss.movie4u.data.models.ActorApiModel
import com.agotakiss.movie4u.data.network.MovieDbApi
import com.agotakiss.movie4u.domain.models.Actor
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ActorRepositoryImplTest {

    @Mock
    lateinit var movieDbApi: MovieDbApi

    lateinit var repository: ActorRepositoryImpl

    @Before
    fun setup() {
        repository = ActorRepositoryImpl(movieDbApi)
    }

    @Test
    fun shouldReturnProperActor() {

        val testId = 123
        val actorApiModel = ActorApiModel(id = 123, name = "testActor1")
        val actor = Actor(id = 123, name = "testActor1")

        whenever(movieDbApi.getActor(any()))
            .thenReturn(Single.just(actorApiModel))

        val testObservable = repository.getActor(testId).test()

        testObservable
            .assertComplete()
            .assertNoErrors()
            .assertResult(actor)
    }
}
