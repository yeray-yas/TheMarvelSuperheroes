package com.yeray_yas.marvelsuperheroes.data.repository

import com.yeray_yas.marvelsuperheroes.data.model.GetCharacterByIdResponse
import com.yeray_yas.marvelsuperheroes.data.network.remote.NetworkLayer

class SharedRepository {

    suspend fun getCharacterById(characterId: Int): GetCharacterByIdResponse? {
        val request = NetworkLayer.apiClient.getCharacterById(characterId)

        if (request.failed){
            return null
        }

        if (!request.isSuccessful){
            return null
        }

        // request is Successful
     return request.body
    }
}
