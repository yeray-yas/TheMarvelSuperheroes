package com.yeray_yas.marvelsuperheroes.data.network.remote

import com.yeray_yas.marvelsuperheroes.data.model.GetCharacterByIdResponse
import retrofit2.Response

class ApiClient(private val marvelApiService: MarvelApiService) {

    suspend fun getCharacterById(characterId:Int): Response<GetCharacterByIdResponse>{
        return marvelApiService.getCharacterById(characterId)
    }
}
