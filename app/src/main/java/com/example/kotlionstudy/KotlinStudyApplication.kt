package com.example.kotlionstudy

import android.app.Activity
import android.app.Application
import android.os.Bundle

class KotlinStudyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityDestroyed(activity: Activity) {
                (activity as? MainScoped)?.destroyScope()

            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityStopped(activity: Activity) {

            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                (activity as? MainScoped)?.createScope()
            }

            override fun onActivityResumed(activity: Activity) {
            }
        })
    }
}