package com.rollncode.cleanarchitectureexample

import android.app.Application
import com.rollncode.cleanarchitectureexample.di.components.ApplicationComponent
import com.rollncode.cleanarchitectureexample.di.modules.ApplicationModule

@Suppress("unused")
class App : Application() {

    companion object {
        lateinit var applicationComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }
}