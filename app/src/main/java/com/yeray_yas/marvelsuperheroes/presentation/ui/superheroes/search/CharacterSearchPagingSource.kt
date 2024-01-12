package com.yeray_yas.marvelsuperheroes.presentation.ui.superheroes.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yeray_yas.marvelsuperheroes.data.network.remote.MarvelApiService
import com.yeray_yas.marvelsuperheroes.domain.model.Character
import com.yeray_yas.marvelsuperheroes.utils.ErrorUtils
import retrofit2.HttpException
import java.io.IOException

class CharacterSearchPagingSource(
    private val apiService: MarvelApiService,
    private val searchQuery: String
): PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        /*val pageNumber = params.key ?: 0
        val previousKey = if (pageNumber == 0) null else pageNumber - 1*/
        try {
            val page = params.key ?: 0
            val limit = params.loadSize
            val offset = page * limit

            val response = apiService.getCharactersPageByName(
                nameStartsWith = searchQuery,
            /*    apiKey = API_KEY,
                hash = HASH,
                ts = TS,*/
                limit = limit,
                offset = offset
            )

            if (response.isSuccessful) {
                val superheroes = response.body()
                return if (superheroes != null) {
                    val prevKey = if (page == 0) null else page - 1
                    val nextKey = if (superheroes == null) null else page + 1

                    LoadResult.Page(
                        data = emptyList(), // todo: <--- antes estaba superheroes: sustituir emptyList()
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                } else {
                    // La respuesta contiene un cuerpo, pero no hay datos de superhéroes
                    LoadResult.Error(Exception("No se encontraron superhéroes"))
                }
            } else {
                // La respuesta no fue exitosa
                val errorMessage = ErrorUtils.getErrorMessage(response.code(), searchQuery)
                return LoadResult.Error(Exception(errorMessage))
            }
        }catch (e: IOException) {
            // Error de conexión a Internet
            return LoadResult.Error(Exception(ErrorUtils.getNetworkErrorMessage(), e))
        } catch (e: HttpException) {
            // Error HTTP
            return LoadResult.Error(Exception(ErrorUtils.getErrorMessage(e.code(), searchQuery), e))
        } catch (e: Exception) {
            // Otros errores
            return LoadResult.Error(Exception(ErrorUtils.getErrorMessage(0, searchQuery), e))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        // Try to find the page key of the closest page to anchorPosition from
        // either the prevKey or the nextKey; you need to handle nullability
        // here.
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey are null -> anchorPage is the
        //    initial page, so return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}