package com.yeray_yas.marvelsuperheroes.data.pagination.character.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.android.material.internal.ViewUtils.hideKeyboard
import com.yeray_yas.marvelsuperheroes.data.network.remote.NetworkLayer
import com.yeray_yas.marvelsuperheroes.domain.mappers.CharacterDataMapper.toCharacter
import com.yeray_yas.marvelsuperheroes.domain.model.Character
import retrofit2.HttpException
import java.io.IOException
import com.yeray_yas.marvelsuperheroes.presentation.ui.jetpack_navigation.NavGraphActivity

class CharacterSearchPagingSource(
    private val searchQuery: String,
    private val localExceptionCallback: (LocalException) -> Unit
) : PagingSource<Int, Character>() {

    sealed class LocalException(
        val title: String,
        val description: String = ""
    ) : Exception() {
        data object EmptySearch : LocalException(
            title = "Start typing to search!"
        )

        data object NoResults : LocalException(
            title = "Whoops!",
            description = "Looks like your search returned no results"
        )

        data object NetworkError : LocalException(
            title = "Network error occurred"
        )

        data class HttpError(val code: Int, val msg: String) : LocalException(
            title = "HTTP Error $code: $msg"
        )

        data object UnknownError : LocalException(
            title = "Unknown error occurred"
        )
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        if (searchQuery.isEmpty()) {
            val exception = LocalException.EmptySearch
            localExceptionCallback(exception)
            return LoadResult.Error(exception)
        }

        val pageNumber = params.key ?: 0
        val limit = params.loadSize
        val offset = pageNumber * limit

        val response = NetworkLayer.apiClient.getCharactersPageByName(
            nameStartsWith = searchQuery,
            limit = limit,
            offset = offset
        )

        if (response.body.data.offset == 0 && response.body.data.limit == 60 && response.body.data.total == 0) {
            val exception = LocalException.NoResults
            localExceptionCallback(exception)
            return LoadResult.Error(exception)
        }

        response.exception?.let {
            val exception = when (it) {
                is IOException -> LocalException.NetworkError
                is HttpException -> LocalException.HttpError(it.code(), it.message())
                else -> LocalException.UnknownError
            }
            localExceptionCallback(exception)
            return LoadResult.Error(exception)
        }

        println("Status: ${response.body.status}")

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
            val errorMessage = "HTTP Error ${response.body.code}"
            return LoadResult.Error(Exception(errorMessage))
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    }
}
