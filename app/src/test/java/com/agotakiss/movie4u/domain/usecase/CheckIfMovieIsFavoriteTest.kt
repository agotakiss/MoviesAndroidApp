package com.agotakiss.movie4u.domain.usecase

import com.agotakiss.movie4u.domain.models.Movie
import com.agotakiss.movie4u.domain.repository.MovieRepository
import com.agotakiss.movie4u.testhelper.createTestMovies
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class CheckIfMovieIsFavoriteTest {

    @Mock
    lateinit var movieRepository: MovieRepository
    lateinit var useCase: CheckIfMovieIsFavorite

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        useCase = CheckIfMovieIsFavorite(movieRepository)
    }

    @Test
    fun shouldReturnTrueIfFavoritesContainsMovie() {
        val favoriteMovies = createTestMovies()
        val testMovie = favoriteMovies.first()
        whenever(movieRepository.getFavoriteMovies())
            .thenReturn(Single.just(favoriteMovies))

        val testObserver = useCase.check(testMovie).test()

        testObserver
            .assertComplete()
            .assertResult(true)
    }

    @Test
    fun shouldReturnFalseIfMovieIsNotFavorite() {
        val favoriteMovies = createTestMovies()
        val testMovie = Movie(id = 0, title = "False Test")
        whenever(movieRepository.getFavoriteMovies())
            .thenReturn(Single.just(favoriteMovies))

        val testObserver = useCase.check(testMovie).test()

        testObserver
            .assertComplete()
            .assertResult(false)
    }
}