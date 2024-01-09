package com.yeray_yas.marvelsuperheroes.data.network.remote

import com.yeray_yas.marvelsuperheroes.data.model.GetCharacterByIdResponse
import com.yeray_yas.marvelsuperheroes.data.model.GetCharactersPageResponse
import com.yeray_yas.utils.SimpleResponse
import retrofit2.Response

class ApiClient(val marvelApiService: MarvelApiService) {

    suspend fun getCharacterById(characterId: Int): SimpleResponse<GetCharacterByIdResponse> {
        return safeApiCall { marvelApiService.getCharacterById(characterId) }
    }

    suspend fun getCharactersPage(limit:Int, offset: Int): SimpleResponse<GetCharactersPageResponse> {
        return safeApiCall { marvelApiService.getCharactersPage(limit, offset) }
    }

    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            SimpleResponse.failure(e)
        }
    }
}
