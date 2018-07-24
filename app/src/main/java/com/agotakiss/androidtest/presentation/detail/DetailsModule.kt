package com.agotakiss.androidtest.presentation.detail

import com.agotakiss.androidtest.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides

@Module
class DetailsModule(val activity: DetailsActivity) {
    @Provides
    fun providePresenter(movieRepository: MovieRepository): DetailsPresenter = DetailsPresenter(movieRepository)
}