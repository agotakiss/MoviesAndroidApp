package com.agotakiss.movie4u.domain.usecase

import com.agotakiss.movie4u.domain.helper.FavoriteMovieMerger
import com.agotakiss.movie4u.domain.models.Movie
import com.agotakiss.movie4u.domain.paging.Pager
import com.agotakiss.movie4u.domain.repository.MovieRepository
import com.agotakiss.movie4u.testhelper.createTestMovies
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetPopularMoviesTest {
    @Mock
    lateinit var movieRepository: MovieRepository

    @Mock
    lateinit var pager: Pager<Movie>

    @Mock
    lateinit var favoriteMovieMerger: FavoriteMovieMerger

    lateinit var useCase: GetPopularMovies

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        useCase = GetPopularMovies(movieRepository, favoriteMovieMerger, pager)
    }

    @Test
    fun shouldReturnAllMovies() {
        val popularMovies = createTestMovies()
        val favoriteMovies = createTestMovies().subList(0, 1)

        whenever(movieRepository.getFavoriteMovies())
            .thenReturn(Single.just(favoriteMovies))
        whenever(pager.getNextPage())
            .thenReturn(Single.just(popularMovies))
        whenever(favoriteMovieMerger.merge(any(), any()))
            .thenReturn(popularMovies)

        val testObserver = useCase.get().test()

        testObserver
            .assertComplete()
            .assertNoErrors()
            .assertValue { results ->
                popularMovies.forEach { popularMovie ->
                    assertTrue(results.find { it.id == popularMovie.id } != null)
                }

                true
            }
        val favoriteCaptor = argumentCaptor<List<Movie>>()
        val popularCaptor = argumentCaptor<List<Movie>>()

        verify(favoriteMovieMerger).merge(favoriteCaptor.capture(), popularCaptor.capture())
        assertEquals(favoriteMovies, favoriteCaptor.firstValue)
        assertEquals(popularMovies, popularCaptor.firstValue)
    }
}