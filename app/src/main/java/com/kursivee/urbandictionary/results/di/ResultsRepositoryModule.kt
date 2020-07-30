package com.kursivee.urbandictionary.results.di

import com.kursivee.urbandictionary.results.data.repository.ResultsRepositoryImpl
import com.kursivee.urbandictionary.results.domain.repository.ResultsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ResultsRepositoryModule {

    @Binds
    @ActivityRetainedScoped
    abstract fun bindResultsRepository(resultsRepository: ResultsRepositoryImpl): ResultsRepository
}
