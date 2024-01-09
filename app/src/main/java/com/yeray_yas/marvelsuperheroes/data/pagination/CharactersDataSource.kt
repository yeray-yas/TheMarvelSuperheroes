package com.yeray_yas.marvelsuperheroes.data.pagination

import androidx.paging.PageKeyedDataSource
import com.yeray_yas.marvelsuperheroes.data.model.CharacterData
import com.yeray_yas.marvelsuperheroes.data.repository.CharactersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CharactersDataSource(
    private val coroutineScope: CoroutineScope,
    private val repository: CharactersRepository
) : PageKeyedDataSource<Int, CharacterData>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, CharacterData>
    ) {
        coroutineScope.launch {
            fetchData(0, params.requestedLoadSize) { data, nextKey ->
                callback.onResult(data, null, nextKey)
            }
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, CharacterData>
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
        callback: LoadCallback<Int, CharacterData>
    ) {
        // nothing to do
    }

    private suspend fun fetchData(
        page: Int,
        pageSize: Int,
        callback: (List<CharacterData>, Int?) -> Unit
    ) {
        try {
            val offset = page * pageSize
            val response = repository.getCharactersPage(limit = pageSize, offset = offset)

            if (response != null && response.code == 200) {
                val superheroes: List<CharacterData> = response.data.results
                val nextKey = if (superheroes.isEmpty()) null else page + 1
                callback(superheroes, nextKey)
            } else {
                // Manejar errores específicos según tus necesidades o imprimir logs
            }
        } catch (e: Exception) {
            // Manejar errores o imprimir logs
        }
    }

}