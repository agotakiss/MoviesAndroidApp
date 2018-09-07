package com.agotakiss.movie4u.presentation.main.search

import com.agotakiss.movie4u.domain.repository.MovieRepository
import com.agotakiss.movie4u.domain.usecase.GetSearchResults
import dagger.Module
import dagger.Provides

@Module
class SearchModule(val searchFragment: SearchFragment) {
    @Provides
    fun providePresenter(movieRepository: MovieRepository, getSearchResults: GetSearchResults): SearchPresenter = SearchPresenter(movieRepository, getSearchResults)
}