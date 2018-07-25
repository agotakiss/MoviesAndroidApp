package com.agotakiss.androidtest.presentation.actor

import com.agotakiss.androidtest.base.BasePresenter
import com.agotakiss.androidtest.domain.models.Actor
import com.agotakiss.androidtest.domain.repository.ActorRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ActorDetailsPresenter @Inject constructor(
    private val actorRepository: ActorRepository
) : BasePresenter() {

    private var actorId: Int = 0
    lateinit var view: ActorDetailsView

    fun onViewReady(view: ActorDetailsView, actorId: Int) {
        this.view = view
        this.actorId = actorId
        loadActor()
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
}
