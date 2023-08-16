package com.example.marvelcharacters.data.datasource

import com.example.marvelcharacters.core.network.NetworkResult
import com.example.marvelcharacters.core.network.handleApi
import com.example.marvelcharacters.data.dto.BaseResponse
import com.example.marvelcharacters.data.dto.CharacterDTO
import com.example.marvelcharacters.data.dto.PaginatedDataDTO
import com.example.marvelcharacters.data.service.CharacterService

class CharacterApi(
    private val characterService: CharacterService
) : CharacterRemoteDataSource {

    override suspend fun getCharactersList(): NetworkResult<BaseResponse<PaginatedDataDTO<CharacterDTO>>> {
        return handleApi { characterService.getHeroesList(offset = 0) }
    }

    override suspend fun getCharacter(characterId: String): NetworkResult<BaseResponse<PaginatedDataDTO<CharacterDTO>>> {
        return handleApi { characterService.getHeroDetails(characterId = characterId, offset = 0) }
    }
    
}