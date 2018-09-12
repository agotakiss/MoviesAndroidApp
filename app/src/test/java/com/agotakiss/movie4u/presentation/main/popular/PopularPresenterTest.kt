package com.agotakiss.movie4u.presentation.main.popular

import com.agotakiss.movie4u.domain.models.Movie
import com.agotakiss.movie4u.domain.repository.MovieRepository
import com.agotakiss.movie4u.domain.usecase.GetPopularMovies
import com.agotakiss.movie4u.testhelper.createTestMovies
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class PopularPresenterTest {

    @Mock
    lateinit var movieRepository: MovieRepository

    @Mock
    lateinit var getPopularMovies: GetPopularMovies

    @Mock
    lateinit var view: PopularView

    lateinit var presenter: PopularPresenter

    @Before
    fun setup() {
        presenter = PopularPresenter(movieRepository, getPopularMovies)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun shouldLoadPopularMoviesWhenViewIsReady() {
        val movieList = createTestMovies()

        whenever(getPopularMovies.get())
            .thenReturn(Single.just(movieList))

        presenter.onViewReady(view)

        val captor = argumentCaptor<List<Movie>>()
        verify(view).showMovies(captor.capture())
        assertEquals(movieList, captor.firstValue)
    }
}