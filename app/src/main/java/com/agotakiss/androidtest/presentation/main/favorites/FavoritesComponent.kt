package com.agotakiss.androidtest.presentation.main.favorites

import dagger.Subcomponent

@Subcomponent(modules = [FavoritesModule::class])
interface FavoritesComponent {

    fun inject(fragment: FavoritesFragment)
}