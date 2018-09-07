package com.agotakiss.movie4u.presentation.actor

import dagger.Subcomponent

@Subcomponent(modules = [(ActorDetailsModule::class)])

interface ActorDetailsComponent {

    fun inject(activity: ActorDetailsActivity)
}