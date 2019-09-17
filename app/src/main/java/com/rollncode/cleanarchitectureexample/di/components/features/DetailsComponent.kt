package com.rollncode.cleanarchitectureexample.di.components.features

import com.rollncode.cleanarchitectureexample.MainViewModel
import com.rollncode.cleanarchitectureexample.di.FeatureScope
import com.rollncode.cleanarchitectureexample.di.modules.features.DetailsModule
import dagger.Subcomponent

@FeatureScope
@Subcomponent(modules = [DetailsModule::class])
interface DetailsComponent {
    fun inject(vm: MainViewModel)
}