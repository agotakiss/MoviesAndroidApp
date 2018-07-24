package com.agotakiss.androidtest.presentation.detail

import dagger.Subcomponent

@Subcomponent(modules = [(DetailsModule::class)])
interface DetailsComponent {

    fun inject(activity: DetailsActivity)

}