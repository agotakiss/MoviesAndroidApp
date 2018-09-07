package com.agotakiss.movie4u.presentation.main.popular

import dagger.Subcomponent

@Subcomponent(modules = [(PopularModule::class)])
interface PopularComponent {

    fun inject(fragment: PopularFragment)
}