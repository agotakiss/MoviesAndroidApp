package com.agotakiss.movie4u.base

import com.agotakiss.movie4u.presentation.actor.ActorDetailsComponent
import com.agotakiss.movie4u.presentation.actor.ActorDetailsModule
import com.agotakiss.movie4u.presentation.detail.DetailsComponent
import com.agotakiss.movie4u.presentation.detail.DetailsModule
import com.agotakiss.movie4u.presentation.main.popular.PopularComponent
import com.agotakiss.movie4u.presentation.main.popular.PopularModule
import com.agotakiss.movie4u.presentation.main.favorites.FavoritesComponent
import com.agotakiss.movie4u.presentation.main.favorites.FavoritesModule
import com.agotakiss.movie4u.presentation.main.search.SearchComponent
import com.agotakiss.movie4u.presentation.main.search.SearchModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    PagerModule::class
])
interface ApplicationComponent {

    fun inject(application: MovieApplication)

    fun plus(popularModule: PopularModule): PopularComponent
    fun plus(detailsModule: DetailsModule): DetailsComponent
    fun plus(actorDetailsModule: ActorDetailsModule): ActorDetailsComponent

    fun plus(favoritesModule: FavoritesModule): FavoritesComponent
    fun plus(searchModule: SearchModule): SearchComponent
}