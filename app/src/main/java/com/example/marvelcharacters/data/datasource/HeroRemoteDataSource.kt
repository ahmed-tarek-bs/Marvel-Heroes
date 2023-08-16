package com.example.marvelcharacters.data.datasource

import com.example.marvelcharacters.core.network.NetworkResult
import com.example.marvelcharacters.data.dto.BaseResponse
import com.example.marvelcharacters.data.dto.HeroDTO
import com.example.marvelcharacters.data.dto.PaginatedDataDTO

interface HeroRemoteDataSource {

    suspend fun getHeroesList(): NetworkResult<BaseResponse<PaginatedDataDTO<HeroDTO>>>

    suspend fun getHero(characterId: String): NetworkResult<BaseResponse<PaginatedDataDTO<HeroDTO>>>
}