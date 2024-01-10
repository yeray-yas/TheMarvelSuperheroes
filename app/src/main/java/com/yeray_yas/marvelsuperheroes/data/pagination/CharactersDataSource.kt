package com.yeray_yas.marvelsuperheroes.data.pagination

import androidx.paging.PageKeyedDataSource
import com.yeray_yas.marvelsuperheroes.data.model.GetCharacterByIdResponse
import com.yeray_yas.marvelsuperheroes.data.repository.CharactersRepository
import com.yeray_yas.marvelsuperheroes.domain.mappers.CharacterDataMapper.toGetCharacterByIdResponse
//import com.yeray_yas.marvelsuperheroes.utils.toGetCharacterByIdResponse
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
        // Not needed for your use case
    }

    private suspend fun fetchData(
        page: Int,
        pageSize: Int,
        callback: (List<GetCharacterByIdResponse>, Int?) -> Unit
    ) {
        try {
            val response = repository.getCharactersList(limit = pageSize, offset = page * pageSize)

            if (response.code == 200) {
                val superheroes: List<GetCharacterByIdResponse> = response.data.results.map {
                    it.toGetCharacterByIdResponse()
                }

                val nextKey = if (superheroes.isEmpty()) null else page + 1
                callback(superheroes, nextKey)
            } else {
                // Handle errors according to your needs or print logs
            }
        } catch (e: Exception) {
            // Handle errors or print logs
        }
    }
}
