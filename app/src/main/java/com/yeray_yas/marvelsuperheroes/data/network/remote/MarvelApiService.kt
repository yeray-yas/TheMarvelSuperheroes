package com.yeray_yas.marvelsuperheroes.data.network.remote

import com.yeray_yas.marvelsuperheroes.data.network.response.GetCharacterByIdResponse
import com.yeray_yas.marvelsuperheroes.data.network.response.GetCharactersPageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApiService {

    @GET("v1/public/characters/{id}?apikey=3de6bbd5de0a40038da2c8fe677fb23b&hash=6097df79049f8fd22ccfe4607c4cc884&ts=1704638021653")
  suspend  fun getCharacterById(
        @Path("id") characterId: Int
    ): Response<GetCharacterByIdResponse>

    @GET("v1/public/characters")
    suspend fun getCharactersPage(
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
        @Query("ts") ts: Long,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<GetCharactersPageResponse>

    @GET("v1/public/characters")
    suspend fun getCharactersPageByName(
        @Query("nameStartsWith") nameStartsWith: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
        @Query("ts") ts: Long,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<GetCharactersPageResponse>
}