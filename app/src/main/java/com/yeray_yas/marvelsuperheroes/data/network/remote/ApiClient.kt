package com.yeray_yas.marvelsuperheroes.data.network.remote

import com.yeray_yas.marvelsuperheroes.data.network.response.GetCharacterByIdResponse
import com.yeray_yas.marvelsuperheroes.data.network.response.GetCharactersPageResponse
import com.yeray_yas.marvelsuperheroes.data.network.response.GetComicByIdResponse
import com.yeray_yas.marvelsuperheroes.utils.SimpleResponse
import retrofit2.Response

class ApiClient(val marvelApiService: MarvelApiService) {

    suspend fun getCharacterById(characterId: Int): SimpleResponse<GetCharacterByIdResponse> {
        return safeApiCall { marvelApiService.getCharacterById(characterId) }
    }

    suspend fun getCharactersPage(limit:Int, offset: Int): SimpleResponse<GetCharactersPageResponse> {
        return safeApiCall { marvelApiService.getCharactersPage(limit, offset) }
    }

    suspend fun getComicById(comicId:Int): SimpleResponse<GetComicByIdResponse> {
        return safeApiCall { marvelApiService.getComicById(comicId) }
    }

  /*  suspend fun getComicRange(comicRange: Int): SimpleResponse<List<GetComicByIdResponse>> {
        return safeApiCall { marvelApiService.getComicRange(comicRange) }
    }*/

    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            SimpleResponse.failure(e)
        }
    }
}
