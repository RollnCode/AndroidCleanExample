package com.rollncode.cleanarchitectureexample.di.modules.features

import com.rollncode.cleanarchitectureexample.di.FeatureScope
import com.rollncode.domain.repository.DetailsRepository
import com.rollncode.domain.usecase.GetDetailsUseCase
import dagger.Module
import dagger.Provides

@Module
class DetailsModule {

    @FeatureScope
    @Provides
    fun provideGetDetailsUseCase(detailsRepository: DetailsRepository) = GetDetailsUseCase(detailsRepository)
}