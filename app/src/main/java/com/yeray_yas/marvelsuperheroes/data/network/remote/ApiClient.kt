package com.yeray_yas.marvelsuperheroes.data.network.remote

import com.yeray_yas.marvelsuperheroes.data.network.response.GetCharacterByIdResponse
import com.yeray_yas.marvelsuperheroes.data.network.response.GetCharactersPageResponse
import com.yeray_yas.marvelsuperheroes.utils.SimpleResponse
import retrofit2.Response

class ApiClient(private val marvelApiService: MarvelApiService) {

    suspend fun getCharacterById(
        characterId: Int
    ): SimpleResponse<GetCharacterByIdResponse> {
        return safeApiCall { marvelApiService.getCharacterById( characterId) }
    }

    suspend fun getCharactersPage(
        apiKey: String,
        hash: String,
        ts: Long,
        limit: Int,
        offset: Int
    ): SimpleResponse<GetCharactersPageResponse> {
        return safeApiCall { marvelApiService.getCharactersPage(apiKey, hash, ts, limit, offset) }
    }

    suspend fun getCharactersPageByName(
        nameStartsWith: String,
        apiKey: String,
        hash: String,
        ts: Long,
        limit: Int,
        offset: Int
    ): SimpleResponse<GetCharactersPageResponse> {
        return safeApiCall {
            marvelApiService.getCharactersPageByName(
                nameStartsWith,
                apiKey,
                hash,
                ts,
                limit,
                offset
            )
        }
    }

    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            SimpleResponse.failure(e)
        }
    }
}
