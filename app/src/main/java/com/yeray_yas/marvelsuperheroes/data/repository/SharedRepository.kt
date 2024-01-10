package com.yeray_yas.marvelsuperheroes.data.repository

import com.yeray_yas.marvelsuperheroes.data.network.remote.NetworkLayer
import com.yeray_yas.marvelsuperheroes.domain.mappers.CharacterMapper
import com.yeray_yas.marvelsuperheroes.domain.model.Character

class SharedRepository {

    suspend fun getCharacterById(characterId: Int): Character? {
        val request = NetworkLayer.apiClient.getCharacterById(characterId)

        if (request.failed) {
            return null
        }

        if (!request.isSuccessful) {
            return null
        }

        // request is Successful
        return CharacterMapper.buildFrom(response = request.body)
    }
}
