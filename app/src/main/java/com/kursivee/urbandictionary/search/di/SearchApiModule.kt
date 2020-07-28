package com.kursivee.urbandictionary.search.di

import com.kursivee.urbandictionary.search.data.source.AutoCompleteApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
object SearchApiModule {
    @Provides
    @ActivityRetainedScoped
    fun provideAutoCompleteApi(retrofit: Retrofit): AutoCompleteApi =
        retrofit.create(AutoCompleteApi::class.java)
}
