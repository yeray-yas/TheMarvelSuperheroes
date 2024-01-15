package com.yeray_yas.marvelsuperheroes.presentation.ui.superheroes.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.yeray_yas.marvelsuperheroes.data.network.response.GetCharacterByIdResponse
import com.yeray_yas.marvelsuperheroes.data.pagination.character.list.CharactersDataSourceFactory
import com.yeray_yas.marvelsuperheroes.data.repository.CharactersRepository
import com.yeray_yas.marvelsuperheroes.utils.Constants.PAGE_SIZE
import com.yeray_yas.marvelsuperheroes.utils.Constants.PREFETCH_DISTANCE

class CharacterViewModel: ViewModel() {
    private val repository = CharactersRepository()

    private val pageListConfig: PagedList.Config = PagedList.Config.Builder()
        .setPageSize(PAGE_SIZE)
        .setPrefetchDistance(PREFETCH_DISTANCE)
        .build()

    private val dataSourceFactory = CharactersDataSourceFactory(viewModelScope, repository)
    val charactersPagedListLiveData: LiveData<PagedList<GetCharacterByIdResponse>> = LivePagedListBuilder(
        dataSourceFactory, pageListConfig).build()

}