package com.agotakiss.androidtest.presentation.actor

import dagger.Subcomponent
import javax.inject.Inject

@Subcomponent(modules = [(ActorDetailsModule::class)])

interface ActorDetailsComponent {

    fun inject(activity: ActorDetailsActivity)
}