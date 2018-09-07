package com.agotakiss.movie4u.presentation.main.popular

import com.agotakiss.movie4u.domain.repository.MovieRepository
import com.agotakiss.movie4u.domain.usecase.GetPopularMovies
import dagger.Module
import dagger.Provides

@Module
class PopularModule(val popularFragment: PopularFragment) {
    @Provides
    fun providePresenter(movieRepository: MovieRepository, getPopularMovies: GetPopularMovies): PopularPresenter = PopularPresenter(movieRepository, getPopularMovies)
}