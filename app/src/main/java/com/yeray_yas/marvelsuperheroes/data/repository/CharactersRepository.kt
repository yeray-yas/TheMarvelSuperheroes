package com.yeray_yas.marvelsuperheroes.data.repository

import com.yeray_yas.marvelsuperheroes.data.network.response.GetCharactersPageResponse
import com.yeray_yas.marvelsuperheroes.data.network.remote.NetworkLayer
import com.yeray_yas.marvelsuperheroes.domain.mappers.CharacterMapper
import com.yeray_yas.marvelsuperheroes.domain.model.Character
import com.yeray_yas.marvelsuperheroes.utils.MarvelCache

class CharactersRepository {

    suspend fun getCharactersPage(
        apikey: String,
        hash: String,
        ts: Long,
        limit: Int, offset: Int
    ): GetCharactersPageResponse? {
        val request = NetworkLayer.apiClient.getCharactersPage(apikey, hash, ts, limit, offset)

        if (request.failed || !request.isSuccessful) {
            return null
        }
        return request.body
    }

    suspend fun getCharacterById(
        characterId: Int,
        apiKey: String,
        hash: String,
        ts: Long
    ): Character? {

        //Check the cache for our character
        val cachedSuperheroCharacter = MarvelCache.characterMap[characterId]
        if (cachedSuperheroCharacter != null) {
            return cachedSuperheroCharacter
        }

        val request = NetworkLayer.apiClient.getCharacterById(characterId, apiKey, hash, ts)

        if (request.failed || !request.isSuccessful) {
            return null
        }

        val character = CharacterMapper.buildFrom(
            response = request.body
        )

        // Update cache & return value
        MarvelCache.characterMap[characterId] = character
        return CharacterMapper.buildFrom(response = request.body)
    }
}
