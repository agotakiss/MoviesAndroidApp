package com.agotakiss.movie4u.presentation.actor

import com.agotakiss.movie4u.base.BasePresenter
import com.agotakiss.movie4u.domain.models.Actor
import com.agotakiss.movie4u.domain.models.Movie
import com.agotakiss.movie4u.domain.repository.ActorRepository
import com.agotakiss.movie4u.domain.repository.MovieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ActorDetailsPresenter @Inject constructor(
    private val actorRepository: ActorRepository,
    private val movieRepository: MovieRepository
) : BasePresenter() {

    private var actorId: Int = 0
    lateinit var view: ActorDetailsView

    fun onViewReady(view: ActorDetailsView, actorId: Int) {
        this.view = view
        this.actorId = actorId
        loadActor()
        loadActorsMovies()
    }

    private fun loadActor() {
        actorRepository.getActor(actorId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ actor ->
                onActorLoaded(actor)
            }, { t -> view.showError(t) })
    }

    private fun onActorLoaded(actor: Actor) {
        view.initUI(actor)
    }

    private fun loadActorsMovies() {
        movieRepository.getActorsMovies(actorId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ actorsMovieList -> onActorsMoviesLoaded(actorsMovieList) }, { throwable ->
                view.showError(throwable)
            })
    }

    private fun onActorsMoviesLoaded(actorsNewMoviesList: List<Movie>) {
        view.showActorsMovies(actorsNewMoviesList)
    }
}
