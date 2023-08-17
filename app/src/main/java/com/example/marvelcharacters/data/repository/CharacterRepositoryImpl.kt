package com.example.marvelcharacters.data.repository

import com.example.marvelcharacters.core.network.NetworkResult
import com.example.marvelcharacters.core.network.createErrorResource
import com.example.marvelcharacters.core.network.createSuccessResource
import com.example.marvelcharacters.core.utils.AppError
import com.example.marvelcharacters.core.utils.Resource
import com.example.marvelcharacters.data.datasource.CharacterRemoteDataSource
import com.example.marvelcharacters.domain.model.MarvelCharacter
import com.example.marvelcharacters.domain.model.PaginatedData
import com.example.marvelcharacters.domain.repository.CharacterRepository

class CharacterRepositoryImpl(
    private val remoteDataSource: CharacterRemoteDataSource
) : CharacterRepository {

    override suspend fun getCharactersList(
        offset: Int,
        pageSize: Int
    ): Resource<PaginatedData<MarvelCharacter>> {
        return when (val networkResult = remoteDataSource.getCharactersList(offset, pageSize)) {

            is NetworkResult.ApiError -> networkResult.createErrorResource()

            is NetworkResult.ApiSuccess -> networkResult.createSuccessResource {
                val paginationData = it.data

                val heroes = it.data?.results?.map { heroDTO -> heroDTO.mapToMarvelHero() }

                return@createSuccessResource PaginatedData(
                    data = heroes ?: listOf(),
                    offSet = paginationData?.offset,
                    pageSize = paginationData?.limit,
                    totalCount = paginationData?.total
                )
            }

        }
    }

    override suspend fun getCharacter(characterId: String): Resource<MarvelCharacter?> {
        return when (val networkResult = remoteDataSource.getCharacter(characterId)) {

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