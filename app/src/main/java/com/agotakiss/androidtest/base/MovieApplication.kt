package com.agotakiss.androidtest.base

import android.app.Application
import timber.log.Timber

class MovieApplication : Application() {

    override fun onCreate() {
        instance = this
        super.onCreate()
        applicationComponent.inject(this)
        Timber.plant(Timber.DebugTree())
    }

    companion object {
        private lateinit var instance: MovieApplication
        fun get(): MovieApplication = instance
    }

    val applicationComponent = DaggerApplicationComponent
        .builder()
        .applicationModule(ApplicationModule(this))
        .build()

}