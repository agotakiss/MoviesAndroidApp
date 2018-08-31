package com.agotakiss.androidtest.base

import com.agotakiss.androidtest.presentation.actor.ActorDetailsComponent
import com.agotakiss.androidtest.presentation.actor.ActorDetailsModule
import com.agotakiss.androidtest.presentation.detail.DetailsComponent
import com.agotakiss.androidtest.presentation.detail.DetailsModule
import com.agotakiss.androidtest.presentation.main.popular.MainComponent
import com.agotakiss.androidtest.presentation.main.popular.MainModule
import com.agotakiss.androidtest.presentation.main.favorites.FavoritesComponent
import com.agotakiss.androidtest.presentation.main.favorites.FavoritesModule
import com.agotakiss.androidtest.presentation.main.search.SearchComponent
import com.agotakiss.androidtest.presentation.main.search.SearchModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    fun inject(application: MovieApplication)

    fun plus(mainModule: MainModule): MainComponent
    fun plus(detailsModule: DetailsModule): DetailsComponent
    fun plus(actorDetailsModule: ActorDetailsModule): ActorDetailsComponent

    fun plus(favoritesModule: FavoritesModule): FavoritesComponent
    fun plus(searchModule: SearchModule): SearchComponent

}