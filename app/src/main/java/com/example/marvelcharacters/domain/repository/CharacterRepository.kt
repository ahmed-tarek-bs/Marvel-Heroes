package com.example.marvelcharacters.domain.repository

import com.example.marvelcharacters.core.utils.Resource
import com.example.marvelcharacters.domain.model.MarvelCharacter
import com.example.marvelcharacters.domain.model.PaginatedData

interface CharacterRepository {

    suspend fun getCharactersList(
        offset: Int,
        pageSize: Int
    ): Resource<PaginatedData<MarvelCharacter>>

    suspend fun getCharacter(characterId: String): Resource<MarvelCharacter?>

}