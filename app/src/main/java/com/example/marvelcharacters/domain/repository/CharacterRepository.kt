package com.example.marvelcharacters.domain.repository

import com.example.marvelcharacters.core.utils.Resource
import com.example.marvelcharacters.domain.model.MarvelCharacter

interface CharacterRepository {

    suspend fun getCharactersList(): Resource<List<MarvelCharacter>>

    suspend fun getCharacter(characterId: String): Resource<MarvelCharacter?>

}