package com.example.marvelcharacters.di

import com.example.marvelcharacters.data.datasource.HeroApi
import com.example.marvelcharacters.data.datasource.HeroRemoteDataSource
import com.example.marvelcharacters.data.repository.HeroRepositoryImpl
import com.example.marvelcharacters.data.service.HeroService
import com.example.marvelcharacters.domain.repository.HeroRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

    @Provides
    fun provideHeroRepository(
        dataSource: HeroRemoteDataSource
    ): HeroRepository = HeroRepositoryImpl(dataSource)

    @Provides
    fun provideHeroRemoteDataSource(
        service: HeroService
    ): HeroRemoteDataSource = HeroApi(service)

    @Provides
    fun provideHeroService(
        retrofitClient: Retrofit
    ): HeroService = retrofitClient.create(HeroService::class.java)

}