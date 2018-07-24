package com.agotakiss.androidtest.presentation.main

import com.agotakiss.androidtest.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides

@Module
class MainModule (val activity: MainActivity){
    @Provides
    fun providePresenter(movieRepository: MovieRepository): MainPresenter = MainPresenter(movieRepository)
}