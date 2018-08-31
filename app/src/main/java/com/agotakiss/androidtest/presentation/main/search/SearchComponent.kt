package com.agotakiss.androidtest.presentation.main.search

import dagger.Subcomponent

@Subcomponent(modules = [SearchModule::class])
interface SearchComponent {
    fun inject(fragment: SearchFragment)
}