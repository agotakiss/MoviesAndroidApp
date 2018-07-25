package com.agotakiss.androidtest.presentation.actor

import com.agotakiss.androidtest.domain.models.Actor
import com.agotakiss.androidtest.domain.models.Cast
import com.agotakiss.androidtest.domain.models.Movie

interface ActorDetailsView {

    fun initUI(actor : Actor)

    fun showError(t : Throwable)
}