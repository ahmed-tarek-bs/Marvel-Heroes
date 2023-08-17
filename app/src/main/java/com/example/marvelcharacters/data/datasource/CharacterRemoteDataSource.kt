package com.example.marvelcharacters.data.datasource

import com.example.marvelcharacters.core.network.NetworkResult
import com.example.marvelcharacters.data.dto.BaseResponse
import com.example.marvelcharacters.data.dto.CharacterDTO
import com.example.marvelcharacters.data.dto.PaginatedDataDTO

interface CharacterRemoteDataSource {

    suspend fun getCharactersList(
        offset: Int,
        pageSize: Int
    ): NetworkResult<BaseResponse<PaginatedDataDTO<CharacterDTO>>>

    suspend fun getCharacter(characterId: String): NetworkResult<BaseResponse<PaginatedDataDTO<CharacterDTO>>>
}