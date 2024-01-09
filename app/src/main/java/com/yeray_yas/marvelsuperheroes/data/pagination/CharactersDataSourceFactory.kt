package com.yeray_yas.marvelsuperheroes.data.pagination

import androidx.paging.DataSource
import com.yeray_yas.marvelsuperheroes.data.model.CharacterData
import com.yeray_yas.marvelsuperheroes.data.repository.CharactersRepository
import kotlinx.coroutines.CoroutineScope

class CharactersDataSourceFactory(
    private val coroutineScope: CoroutineScope,
    private val repository: CharactersRepository
): DataSource.Factory<Int, CharacterData>() {
    override fun create(): DataSource<Int, CharacterData> {
        return CharactersDataSource(coroutineScope, repository)
    }
}