package com.agotakiss.androidtest.presentation.main.popular

import dagger.Subcomponent

@Subcomponent(modules = [(PopularModule::class)])
interface PopularComponent {

    fun inject(fragment: PopularFragment)
}