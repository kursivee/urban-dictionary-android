package com.kursivee.urbandictionary.results.di

import com.kursivee.urbandictionary.results.data.source.GetResultsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
object ResultsApiModule {

    @Provides
    @ActivityRetainedScoped
    fun provideGetResultsApi(retrofit: Retrofit): GetResultsApi {
        return retrofit.create(GetResultsApi::class.java)
    }
}
