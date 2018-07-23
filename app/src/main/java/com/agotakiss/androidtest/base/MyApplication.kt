package com.agotakiss.androidtest.base

import android.app.Application
import com.agotakiss.androidtest.di.ActivityInjector
import javax.inject.Inject

class MyApplication : Application() {

    @Inject
    lateinit var activityInjector: ActivityInjector


    override fun onCreate() {
        super.onCreate()
        val component: ApplicationComponent by lazy {
            DaggerApplicationComponent
                    .builder()
                    .applicationModule(ApplicationModule(this))
                    .build()
        }
        component.inject(this)
    }
}
