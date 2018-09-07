package com.agotakiss.androidtest.presentation.main.popular

import com.agotakiss.androidtest.domain.repository.MovieRepository
import com.agotakiss.androidtest.domain.usecase.GetPopularMovies
import dagger.Module
import dagger.Provides

@Module
class PopularModule(val popularFragment: PopularFragment) {
    @Provides
    fun providePresenter(movieRepository: MovieRepository, getPopularMovies: GetPopularMovies): PopularPresenter = PopularPresenter(movieRepository, getPopularMovies)
}