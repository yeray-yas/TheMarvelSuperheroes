package com.yeray_yas.marvelsuperheroes.data.repository

import com.yeray_yas.marvelsuperheroes.data.network.response.GetCharactersPageResponse
import com.yeray_yas.marvelsuperheroes.data.network.remote.ApiClient

class CharactersRepository(private val apiClient: ApiClient) {

    suspend fun getCharactersList(limit: Int, offset: Int): GetCharactersPageResponse {
        return apiClient.getCharactersPage(limit, offset).body
    }
}
