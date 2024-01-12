package com.yeray_yas.marvelsuperheroes.presentation.ui.superheroes.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yeray_yas.marvelsuperheroes.data.network.remote.NetworkLayer
import com.yeray_yas.marvelsuperheroes.domain.mappers.CharacterDataMapper.toCharacter
import com.yeray_yas.marvelsuperheroes.domain.model.Character
import com.yeray_yas.marvelsuperheroes.utils.ErrorUtils
import retrofit2.HttpException
import java.io.IOException

class CharacterSearchPagingSource(
    private val searchQuery: String
) : PagingSource<Int, Character>() {

    sealed class LocalException(
        val title: String,
        val description: String = ""
    ): Exception() {
        data object EmptySearch : LocalException(
            title = "Start typing to search!"
        )
        data object NoResults : LocalException(
            title = "Whoops!",
            description = "Looks like your search returned no results"
        )
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val pageNumber = params.key ?: 0
        val limit = params.loadSize
        val offset = pageNumber * limit

        try {
            val response = NetworkLayer.apiClient.getCharactersPageByName(
                nameStartsWith = searchQuery,
                limit = limit,
                offset = offset)

            if (response.isSuccessful) {
                val dataPaging = response.body.data
                val characters = dataPaging.results


                val prevKey = if (pageNumber == 0) null else pageNumber - 1
                val nextKey = if (characters.isEmpty()) null else pageNumber + 1

                return LoadResult.Page(
                    data = characters.map { it.toCharacter() },
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            } else {
                val errorMessage =
                    response.data?.let { ErrorUtils.getErrorMessage(it.code(), searchQuery) }
                return LoadResult.Error(Exception(errorMessage))
            }
        } catch (e: IOException) {
            return LoadResult.Error(Exception(ErrorUtils.getNetworkErrorMessage(), e))
        } catch (e: HttpException) {
            return LoadResult.Error(Exception(ErrorUtils.getErrorMessage(e.code(), searchQuery), e))
        } catch (e: Exception) {
            return LoadResult.Error(Exception(ErrorUtils.getErrorMessage(0, searchQuery), e))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}