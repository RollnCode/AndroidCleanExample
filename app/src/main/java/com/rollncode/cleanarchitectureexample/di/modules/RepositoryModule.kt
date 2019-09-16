package com.rollncode.cleanarchitectureexample.di.modules

import com.rollncode.data.dao.DetailsDao
import com.rollncode.data.repository.DetailsDataRepository
import com.rollncode.domain.repository.DetailsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideDetailsRepository(detailsDao: DetailsDao): DetailsRepository = DetailsDataRepository(detailsDao)
}