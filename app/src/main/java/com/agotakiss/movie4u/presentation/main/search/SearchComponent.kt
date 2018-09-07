package com.agotakiss.movie4u.presentation.main.search

import dagger.Subcomponent

@Subcomponent(modules = [SearchModule::class])
interface SearchComponent {
    fun inject(fragment: SearchFragment)
}