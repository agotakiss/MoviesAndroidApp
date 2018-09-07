package com.agotakiss.movie4u.presentation.main.favorites

import dagger.Subcomponent

@Subcomponent(modules = [FavoritesModule::class])
interface FavoritesComponent {

    fun inject(fragment: FavoritesFragment)
}