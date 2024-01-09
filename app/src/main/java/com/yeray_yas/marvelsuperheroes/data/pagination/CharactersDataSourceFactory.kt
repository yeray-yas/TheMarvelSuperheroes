package com.yeray_yas.marvelsuperheroes.data.pagination

import androidx.paging.DataSource
import com.yeray_yas.marvelsuperheroes.data.model.GetCharacterByIdResponse
import com.yeray_yas.marvelsuperheroes.data.repository.CharactersRepository
import kotlinx.coroutines.CoroutineScope

class CharactersDataSourceFactory(
    private val coroutineScope: CoroutineScope,
    private val repository: CharactersRepository
): DataSource.Factory<Int, GetCharacterByIdResponse>() {
    override fun create(): DataSource<Int, GetCharacterByIdResponse> {
        return CharactersDataSource(coroutineScope, repository)
    }
}