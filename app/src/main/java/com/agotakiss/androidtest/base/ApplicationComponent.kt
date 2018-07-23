package com.agotakiss.androidtest.base

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(ApplicationModule::class), ActivityBindingModule::class])
interface ApplicationComponent {
    fun inject(application: MyApplication)
}