package com.agotakiss.androidtest.presentation.main.favorites

import com.agotakiss.androidtest.base.BasePresenter
import com.agotakiss.androidtest.domain.repository.MovieRepository
import javax.inject.Inject

class FavoritesPresenter @Inject constructor(
    private val movieRepository: MovieRepository
) : BasePresenter() {

    lateinit var view: FavoritesView

    fun onViewReady(view: FavoritesView) {
        this.view = view
    }

}