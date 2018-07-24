package com.agotakiss.androidtest.base

import android.app.Activity
import com.agotakiss.androidtest.presentation.detail.DetailsComponent
import com.agotakiss.androidtest.presentation.detail.DetailsModule
import com.agotakiss.androidtest.presentation.main.MainComponent
import com.agotakiss.androidtest.presentation.main.MainModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    fun inject(application: MovieApplication)

    fun plus(mainModule: MainModule): MainComponent
    fun plus(detailsModule: DetailsModule): DetailsComponent

}