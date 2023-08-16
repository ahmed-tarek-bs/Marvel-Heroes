package com.example.marvelcharacters.di

import com.example.marvelcharacters.data.datasource.CharacterApi
import com.example.marvelcharacters.data.datasource.CharacterRemoteDataSource
import com.example.marvelcharacters.data.repository.CharacterRepositoryImpl
import com.example.marvelcharacters.data.service.CharacterService
import com.example.marvelcharacters.domain.repository.CharacterRepository
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
        dataSource: CharacterRemoteDataSource
    ): CharacterRepository = CharacterRepositoryImpl(dataSource)

    @Provides
    fun provideHeroRemoteDataSource(
        service: CharacterService
    ): CharacterRemoteDataSource = CharacterApi(service)

    @Provides
    fun provideHeroService(
        retrofitClient: Retrofit
    ): CharacterService = retrofitClient.create(CharacterService::class.java)

}