package com.agotakiss.androidtest.presentation.main

import dagger.Subcomponent

@Subcomponent(modules = [(MainModule::class)])
interface MainComponent {

    fun inject(fragment: MainFragment)

}