package com.example.marvelcharacters.di

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
            baseURL = "http://gateway.marvel.com/v1/public/",
            authToken = "e959b603bcec131723e4e651784ad340",
            hash = "2e25bc2d763f933b6f616bd4d0b76023",
            timeStamp = "1691528326"
        )
    }

    @Provides
    @Singleton
    fun provideRetrofitClient(marvelApi: MarvelApi): Retrofit {
        return marvelApi.retrofitClient
    }

}