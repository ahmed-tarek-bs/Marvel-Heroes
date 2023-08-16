package com.example.marvelcharacters.data.repository

import com.example.marvelcharacters.core.network.NetworkResult
import com.example.marvelcharacters.core.network.createErrorResource
import com.example.marvelcharacters.core.network.createSuccessResource
import com.example.marvelcharacters.core.utils.AppError
import com.example.marvelcharacters.core.utils.Resource
import com.example.marvelcharacters.data.datasource.HeroRemoteDataSource
import com.example.marvelcharacters.domain.model.MarvelHero
import com.example.marvelcharacters.domain.repository.HeroRepository

class HeroRepositoryImpl(
    private val remoteDataSource: HeroRemoteDataSource
) : HeroRepository {

    override suspend fun getHeroesList(): Resource<List<MarvelHero>> {
        return when (val networkResult = remoteDataSource.getHeroesList()) {

            is NetworkResult.ApiError -> networkResult.createErrorResource()

            is NetworkResult.ApiSuccess -> networkResult.createSuccessResource {
                val heroes = it.data?.results?.map { heroDTO -> heroDTO.mapToMarvelHero() }
                return@createSuccessResource heroes ?: listOf()
            }

        }
    }

    override suspend fun getHero(characterId: String): Resource<MarvelHero?> {
        return when (val networkResult = remoteDataSource.getHeroesList()) {

            is NetworkResult.ApiError -> networkResult.createErrorResource()

            is NetworkResult.ApiSuccess -> {
                networkResult.successData.data?.results?.get(0)?.mapToMarvelHero()
                    .let { marvelHero ->
                        if (marvelHero != null)
                            Resource.Success(marvelHero)
                        else
                            Resource.Error(AppError.UnExpectedError)
                    }
            }

        }
    }

}