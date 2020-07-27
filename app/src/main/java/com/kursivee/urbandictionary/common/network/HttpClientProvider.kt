package com.kursivee.urbandictionary.common.network

import okhttp3.HttpUrl
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object HttpClientProvider {
    fun provideRetrofit(baseUrl: HttpUrl): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
}
