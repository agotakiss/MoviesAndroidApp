package com.agotakiss.androidtest.presentation.main

import dagger.Module
import dagger.Provides

@Module
class MainModule (val activity: MainActivity){
    @Provides
    fun providePresenter(mainPresenterImpl: MainPresenterImpl): MainPresenter = mainPresenterImpl
}