package com.kursivee.urbandictionary.common.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object HttpClientProvider {
    fun provideRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
}
