package com.agotakiss.movie4u.domain.usecase

import com.agotakiss.movie4u.domain.helper.FavoriteMovieMerger
import com.agotakiss.movie4u.domain.models.Movie
import com.agotakiss.movie4u.domain.paging.Pager
import com.agotakiss.movie4u.domain.paging.PagerFactory
import com.agotakiss.movie4u.domain.paging.PagingType
import com.agotakiss.movie4u.domain.repository.MovieRepository
import com.agotakiss.movie4u.testhelper.createTestMovies
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals

class GetSearchResultsTest {

    @Mock
    lateinit var movieRepository: MovieRepository

    @Mock
    lateinit var pagerFactory: PagerFactory

    @Mock
    lateinit var pager: Pager<Movie>

    @Mock
    lateinit var favoriteMovieMerger: FavoriteMovieMerger

    lateinit var usecase: GetSearchResults

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        usecase = GetSearchResults(movieRepository, pagerFactory, favoriteMovieMerger)
    }

    @Test
    fun shouldCreateNewPagerIfItWasNull() {
        whenever(pagerFactory.createPager(any(), any()))
            .thenReturn(pager)
        whenever(pager.getNextPage())
            .thenReturn(Single.just(createTestMovies()))
        whenever(movieRepository.getFavoriteMovies())
            .thenReturn(Single.just(createTestMovies()))
        val testParam = "asd"

        val testObserver = usecase.get("asd").test()

        testObserver
            .assertComplete()

        val typeCaptor = argumentCaptor<PagingType>()
        val paramCaptor = argumentCaptor<Any>()

        verify(pagerFactory).createPager(typeCaptor.capture(), paramCaptor.capture())
        assertEquals(PagingType.SEARCH_MOVIES, typeCaptor.firstValue)
        assertEquals(testParam, paramCaptor.firstValue)
    }

    @Test
    fun shouldCreateNewPagerIfQueryStringChanged() {
        val testParam1 = "asd"
        val testParam2 = "123"
        whenever(pagerFactory.createPager(any(), any()))
            .thenReturn(pager)
        whenever(pager.getNextPage())
            .thenReturn(Single.just(createTestMovies()))
        whenever(pager.param).thenReturn(testParam1)
        whenever(movieRepository.getFavoriteMovies())
            .thenReturn(Single.just(createTestMovies()))

        usecase.get(testParam1).blockingGet()
        usecase.get(testParam2).blockingGet()

        val typeCaptor = argumentCaptor<PagingType>()
        val paramCaptor = argumentCaptor<Any>()

        verify(pagerFactory, times(2)).createPager(typeCaptor.capture(), paramCaptor.capture())
        assertEquals(PagingType.SEARCH_MOVIES, typeCaptor.secondValue)
        assertEquals(testParam2, paramCaptor.secondValue)
    }

    @Test
    fun shouldNotCreateNewPagerIfNothingChanged() {
        val testParam1 = "asd"
        whenever(pagerFactory.createPager(any(), any()))
            .thenReturn(pager)
        whenever(pager.getNextPage())
            .thenReturn(Single.just(createTestMovies()))
        whenever(pager.param).thenReturn(testParam1)
        whenever(movieRepository.getFavoriteMovies())
            .thenReturn(Single.just(createTestMovies()))

        usecase.get(testParam1).blockingGet()
        usecase.get(testParam1).blockingGet()

        val typeCaptor = argumentCaptor<PagingType>()
        val paramCaptor = argumentCaptor<Any>()

        verify(pagerFactory).createPager(typeCaptor.capture(), paramCaptor.capture())
    }

    @Test
    fun shouldReturnAllMovies() {
        val searchResults = createTestMovies()
        val favoriteMovies = createTestMovies().subList(0, 1)
        val testParam1 = "asd"

        whenever(pagerFactory.createPager(any(), any()))
            .thenReturn(pager)
        whenever(movieRepository.getFavoriteMovies())
            .thenReturn(Single.just(favoriteMovies))
        whenever(pager.getNextPage())
            .thenReturn(Single.just(searchResults))
        whenever(favoriteMovieMerger.merge(any(), any()))
            .thenReturn(searchResults)

        val testObserver = usecase.get(testParam1).test()

        testObserver
            .assertComplete()
            .assertNoErrors()
            .assertValue(searchResults)

        val favoriteCaptor = argumentCaptor<List<Movie>>()
        val searchCaptor = argumentCaptor<List<Movie>>()

        verify(favoriteMovieMerger).merge(favoriteCaptor.capture(), searchCaptor.capture())
        assertEquals(favoriteMovies, favoriteCaptor.firstValue)
        assertEquals(searchResults, searchCaptor.firstValue)
    }
}