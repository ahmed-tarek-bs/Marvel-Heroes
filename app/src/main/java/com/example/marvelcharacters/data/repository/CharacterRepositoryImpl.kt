package com.example.marvelcharacters.data.repository

import com.example.marvelcharacters.core.network.NetworkResult
import com.example.marvelcharacters.core.network.createErrorResource
import com.example.marvelcharacters.core.network.createSuccessResource
import com.example.marvelcharacters.core.utils.AppError
import com.example.marvelcharacters.core.utils.Resource
import com.example.marvelcharacters.data.datasource.CharacterRemoteDataSource
import com.example.marvelcharacters.domain.model.MarvelCharacter
import com.example.marvelcharacters.domain.repository.CharacterRepository

class CharacterRepositoryImpl(
    private val remoteDataSource: CharacterRemoteDataSource
) : CharacterRepository {

    override suspend fun getCharactersList(): Resource<List<MarvelCharacter>> {
        return when (val networkResult = remoteDataSource.getCharactersList()) {

            is NetworkResult.ApiError -> networkResult.createErrorResource()

            is NetworkResult.ApiSuccess -> networkResult.createSuccessResource {
                val heroes = it.data?.results?.map { heroDTO -> heroDTO.mapToMarvelHero() }
                return@createSuccessResource heroes ?: listOf()
            }

        }
    }

    override suspend fun getCharacter(characterId: String): Resource<MarvelCharacter?> {
        return when (val networkResult = remoteDataSource.getCharactersList()) {

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