package com.example.marvelcharacters.domain.repository

import com.example.marvelcharacters.core.utils.Resource
import com.example.marvelcharacters.domain.model.MarvelHero

interface HeroRepository {

    suspend fun getHeroesList(): Resource<List<MarvelHero>>

    suspend fun getHero(characterId: String): Resource<MarvelHero?>

}