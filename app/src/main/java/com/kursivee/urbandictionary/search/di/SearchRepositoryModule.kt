package com.kursivee.urbandictionary.search.di

import com.kursivee.urbandictionary.search.data.repository.AutoCompleteRepositoryImpl
import com.kursivee.urbandictionary.search.domain.repository.AutoCompleteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class SearchRepositoryModule {

    @Binds
    @ActivityRetainedScoped
    abstract fun bindAutoCompleteRepository(
        autoCompleteRepositoryImpl: AutoCompleteRepositoryImpl
    ): AutoCompleteRepository
}
