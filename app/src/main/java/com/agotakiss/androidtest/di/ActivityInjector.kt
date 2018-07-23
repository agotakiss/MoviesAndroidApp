package com.agotakiss.androidtest.di

import android.app.Activity
import android.content.Context
import com.agotakiss.androidtest.base.BaseActivity
import com.agotakiss.androidtest.base.MyApplication
import dagger.android.AndroidInjector
import javax.inject.Inject
import javax.inject.Provider

class ActivityInjector @Inject constructor(
        private val activityInjectors: Map<Class<out Activity>, Provider<AndroidInjector.Factory<Activity>>>
) {
    private val cache = mutableMapOf<String, AndroidInjector<in Activity>>()

    fun inject(activity: Activity) {
        if (activity !is BaseActivity) {
            throw IllegalStateException("Activity must extend BaseActivity")
        }
        cache.containsKey(activity.instanceId)
                .takeIf { it }
                ?.let { cache.get(activity.instanceId)?.inject(activity) }
                ?: doInjection(activity)
    }

    private fun doInjection(activity: BaseActivity) {
        val injectorFactory = activityInjectors[activity::class.java]?.get()
        val injector = injectorFactory?.create(activity)
        cache.put(activity.instanceId!!, injector!!)
        injector.inject(activity)
    }

    fun clear(activity: Activity) {
        if (activity !is BaseActivity) {
            throw IllegalStateException("Activity must extend BaseActivity")
        }
        cache.remove(activity.instanceId)
    }

    companion object {
        fun get(context: Context):ActivityInjector {
           return (context.applicationContext as MyApplication).activityInjector
        }
    }
}
