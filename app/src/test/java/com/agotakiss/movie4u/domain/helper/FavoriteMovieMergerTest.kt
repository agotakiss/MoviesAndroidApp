package com.agotakiss.movie4u.domain.helper

import com.agotakiss.movie4u.testhelper.createTestMovies
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertTrue

@RunWith(MockitoJUnitRunner::class)
class FavoriteMovieMergerTest {

    @Test
    fun shouldReturnMoviesWithProperValues() {
        val movieList = createTestMovies()
        val favoriteMovies = createTestMovies().subList(0, 1)

        val result = FavoriteMovieMerger().merge(favoriteMovies, movieList)

        favoriteMovies.forEach { favoriteMovie ->
            val favoriteInResults = result.find { it.id == favoriteMovie.id }

            if (favoriteInResults != null) {
                assertTrue(favoriteInResults.isFavorite)
            }
        }
    }

    @Test
    fun shouldReturnAllMovies() {
        val movieList = createTestMovies()
        val favoriteMovies = createTestMovies().subList(0, 1)

        val result = FavoriteMovieMerger().merge(favoriteMovies, movieList)

        movieList.forEach { movie ->
            assertTrue(result.find { it.id == movie.id } != null)
        }
    }
}