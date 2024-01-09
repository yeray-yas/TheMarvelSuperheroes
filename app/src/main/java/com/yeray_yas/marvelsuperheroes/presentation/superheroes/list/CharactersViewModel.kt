package com.yeray_yas.marvelsuperheroes.presentation.superheroes.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.yeray_yas.marvelsuperheroes.data.model.CharacterData
import com.yeray_yas.marvelsuperheroes.data.pagination.CharactersDataSourceFactory
import com.yeray_yas.marvelsuperheroes.data.repository.CharactersRepository
import com.yeray_yas.utils.Constants.PAGE_SIZE
import com.yeray_yas.utils.Constants.PREFETCH_DISTANCE

class CharactersViewModel: ViewModel() {

    private val repository = CharactersRepository()
    private val pageListConfig: PagedList.Config = PagedList.Config.Builder()
        .setPageSize(PAGE_SIZE)
        .setPrefetchDistance(PREFETCH_DISTANCE)
        .build()

    private val dataSourceFactory = CharactersDataSourceFactory(viewModelScope, repository)
    val charactersPagedListLiveData: LiveData<PagedList<CharacterData>> = LivePagedListBuilder(
        dataSourceFactory, pageListConfig
    ).build()

}