package com.example.marvelcharacters.data.service

import com.example.marvelcharacters.data.dto.BaseResponse
import com.example.marvelcharacters.data.dto.HeroDTO
import com.example.marvelcharacters.data.dto.PaginatedDataDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HeroService {

    @GET("http://gateway.marvel.com/v1/public/characters")
    suspend fun getHeroesList(
        @Query("offset") offset: Int
    ): Response<BaseResponse<PaginatedDataDTO<HeroDTO>>>

    @GET("http://gateway.marvel.com/v1/public/characters/{characterId}")
    suspend fun getHeroDetails(
        @Path("characterId") characterId: String,
        @Query("offset") offset: Int = 0
    ): Response<BaseResponse<PaginatedDataDTO<HeroDTO>>>

}