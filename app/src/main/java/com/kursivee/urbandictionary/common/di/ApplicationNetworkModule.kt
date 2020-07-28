package com.kursivee.urbandictionary.common.di

import com.kursivee.urbandictionary.common.network.HttpClientProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class ApplicationNetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return HttpClientProvider.provideRetrofit(
            "https://api.urbandictionary.com/"
        )
    }
}
