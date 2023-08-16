package com.example.marvelcharacters.data.datasource

import com.example.marvelcharacters.core.network.NetworkResult
import com.example.marvelcharacters.core.network.handleApi
import com.example.marvelcharacters.data.dto.BaseResponse
import com.example.marvelcharacters.data.dto.HeroDTO
import com.example.marvelcharacters.data.dto.PaginatedDataDTO
import com.example.marvelcharacters.data.service.HeroService

class HeroApi(
    private val heroService: HeroService
) : HeroRemoteDataSource {

    override suspend fun getHeroesList(): NetworkResult<BaseResponse<PaginatedDataDTO<HeroDTO>>> {
        return handleApi { heroService.getHeroesList(offset = 0) }
    }

    override suspend fun getHero(characterId: String): NetworkResult<BaseResponse<PaginatedDataDTO<HeroDTO>>> {
        return handleApi { heroService.getHeroDetails(characterId = characterId, offset = 0) }
    }
    
}