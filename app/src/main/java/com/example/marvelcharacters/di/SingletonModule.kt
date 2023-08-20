package com.example.marvelcharacters.di

import com.example.marvelcharacters.BuildConfig
import com.example.marvelcharacters.core.network.MarvelApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {

    @Provides
    @Singleton
    fun provideAbdApi(): MarvelApi {
        return MarvelApi(
            baseURL = BuildConfig.MARVEL_BASE_URL,
            privateKey = BuildConfig.MARVEL_PRIVATE_KEY,
            publicKey = BuildConfig.MARVEL_PUBLIC_KEY
        )
    }

    @Provides
    @Singleton
    fun provideRetrofitClient(marvelApi: MarvelApi): Retrofit {
        return marvelApi.retrofitClient
    }

}