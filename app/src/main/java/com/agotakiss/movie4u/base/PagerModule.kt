package com.agotakiss.movie4u.base

import com.agotakiss.movie4u.domain.models.Movie
import com.agotakiss.movie4u.domain.paging.Pager
import com.agotakiss.movie4u.domain.paging.PagerFactory
import com.agotakiss.movie4u.domain.paging.PagingType
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class PagerModule {

    @Named("popular")
    @Singleton
    @Provides
    fun providePopularMoviePager(pagerFactory: PagerFactory): Pager<Movie> =
        pagerFactory.createPager(PagingType.POPULAR_MOVIES)

    @Named("similar")
    @Singleton
    @Provides
    fun provideSimilarMoviePager(pagerFactory: PagerFactory): Pager<Movie> =
        pagerFactory.createPager(PagingType.SIMILAR_MOVIES)
}