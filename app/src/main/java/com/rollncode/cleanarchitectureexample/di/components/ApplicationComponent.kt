package com.rollncode.cleanarchitectureexample.di.components

import android.app.Application
import com.rollncode.cleanarchitectureexample.di.components.features.DetailsComponent
import com.rollncode.cleanarchitectureexample.di.modules.ApplicationModule
import com.rollncode.cleanarchitectureexample.di.modules.DatabaseModule
import com.rollncode.cleanarchitectureexample.di.modules.RepositoryModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        DatabaseModule::class,
        RepositoryModule::class]
)
interface ApplicationComponent {
    fun inject(app: Application)
    fun detailsComponent(): DetailsComponent
}