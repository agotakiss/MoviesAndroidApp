package com.agotakiss.androidtest.presentation.main.search

import com.agotakiss.androidtest.domain.repository.MovieRepository
import com.agotakiss.androidtest.domain.usecase.GetSearchResults
import dagger.Module
import dagger.Provides

@Module
class SearchModule(val searchFragment: SearchFragment) {
    @Provides
    fun providePresenter(movieRepository: MovieRepository, getSearchResults: GetSearchResults)
        : SearchPresenter = SearchPresenter(movieRepository, getSearchResults)

}