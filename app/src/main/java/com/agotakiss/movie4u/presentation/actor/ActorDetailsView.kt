package com.agotakiss.movie4u.presentation.actor

import com.agotakiss.movie4u.domain.models.Actor
import com.agotakiss.movie4u.domain.models.Movie

interface ActorDetailsView {

    fun initUI(actor: Actor)

    fun showActorsMovies(actorsNewMoviesList: List<Movie>)

    fun showError(t: Throwable)
}