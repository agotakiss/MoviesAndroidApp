package com.agotakiss.androidtest.base

import android.app.Application
import com.agotakiss.androidtest.BuildConfig
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import io.fabric.sdk.android.Fabric
import timber.log.Timber


class MovieApplication : Application() {

    override fun onCreate() {
        instance = this
        super.onCreate()
        applicationComponent.inject(this)
        initLogging()
    }

    fun initLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Fabric.with(this, Crashlytics())
            Timber.plant(CrashlyticsTree())
        }
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