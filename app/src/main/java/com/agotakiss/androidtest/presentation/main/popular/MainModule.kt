package com.agotakiss.androidtest.presentation.main.popular

import com.agotakiss.androidtest.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides

@Module
class MainModule (val mainFragment: MainFragment){
    @Provides
    fun providePresenter(movieRepository: MovieRepository): MainPresenter = MainPresenter(movieRepository)
}