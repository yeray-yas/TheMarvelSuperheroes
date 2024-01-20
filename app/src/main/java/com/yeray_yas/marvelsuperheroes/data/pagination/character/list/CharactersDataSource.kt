package com.yeray_yas.marvelsuperheroes.data.pagination.character.list

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.yeray_yas.marvelsuperheroes.data.network.response.GetCharacterByIdResponse
import com.yeray_yas.marvelsuperheroes.data.repository.CharactersRepository
import com.yeray_yas.marvelsuperheroes.domain.mappers.CharacterDataMapper.toGetCharacterByIdResponse
import com.yeray_yas.marvelsuperheroes.utils.Constants.API_KEY
import com.yeray_yas.marvelsuperheroes.utils.Constants.HASH
import com.yeray_yas.marvelsuperheroes.utils.Constants.TS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CharactersDataSource(
    private val coroutineScope: CoroutineScope,
    private val repository: CharactersRepository
) : PageKeyedDataSource<Int, GetCharacterByIdResponse>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, GetCharacterByIdResponse>
    ) {
        coroutineScope.launch {
            fetchData(0, params.requestedLoadSize) { data, nextKey ->
                callback.onResult(data, null, nextKey)
            }
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, GetCharacterByIdResponse>
    ) {
        coroutineScope.launch {
            val page = params.key
            fetchData(page, params.requestedLoadSize) { data, nextKey ->
                callback.onResult(data, nextKey)
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, GetCharacterByIdResponse>
    ) {
        // Not needed
    }

    private suspend fun fetchData(
        page: Int,
        pageSize: Int,
        callback: (List<GetCharacterByIdResponse>, Int?) -> Unit
    ) {
        try {
            val response = repository.getCharactersPage(
                limit = pageSize,
                offset = page * pageSize,
                apikey = API_KEY,
                hash = HASH,
                ts = TS
            )

            if (response != null) {
                if (response.code == 200) {
                    val superheroes: List<GetCharacterByIdResponse> = response.data.results.map {
                        it.toGetCharacterByIdResponse()
                    }

                    val nextKey = if (superheroes.isEmpty()) null else page + 1
                    callback(superheroes, nextKey)
                } else {
                    Log.i("ResponseError", "There's an error with code: ${response.code}")
                }
            }
        } catch (e: Exception) {
            Log.e("ExceptionError", "There's an error: ${e.message}")
        }
    }
}
