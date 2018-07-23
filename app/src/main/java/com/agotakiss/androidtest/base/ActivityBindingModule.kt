package com.agotakiss.androidtest.base

import android.app.Activity
import com.agotakiss.androidtest.presentation.main.MainActivity
import com.agotakiss.androidtest.presentation.main.MainActivityComponent
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Module(subcomponents = [(MainActivity::class)])
abstract class ActivityBindingModule {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    abstract fun provideMainActivityInjector(builder: MainActivityComponent.Builder)
            : AndroidInjector.Factory<out Activity>
}