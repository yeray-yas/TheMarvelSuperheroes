package com.yeray_yas.marvelsuperheroes.data.repository

import com.yeray_yas.marvelsuperheroes.data.model.GetCharactersPageResponse
import com.yeray_yas.marvelsuperheroes.data.network.remote.NetworkLayer

class CharactersRepository {

    suspend fun getCharactersPage(limit:Int, offset: Int): GetCharactersPageResponse? {
        val request = NetworkLayer.apiClient.getCharactersPage(limit, offset)

        if (request.failed || !request.isSuccessful) {
            return null
        }

        return request.body
    }
}