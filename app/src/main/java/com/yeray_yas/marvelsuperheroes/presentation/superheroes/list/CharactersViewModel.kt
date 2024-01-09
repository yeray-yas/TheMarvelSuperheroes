package com.yeray_yas.marvelsuperheroes.presentation.superheroes.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.yeray_yas.marvelsuperheroes.data.model.GetCharacterByIdResponse
import com.yeray_yas.marvelsuperheroes.data.network.remote.ApiClient
import com.yeray_yas.marvelsuperheroes.data.network.remote.NetworkLayer
import com.yeray_yas.marvelsuperheroes.data.pagination.CharactersDataSourceFactory
import com.yeray_yas.marvelsuperheroes.data.repository.CharactersRepository
import com.yeray_yas.utils.Constants.PAGE_SIZE
import com.yeray_yas.utils.Constants.PREFETCH_DISTANCE

class CharactersViewModel: ViewModel() {

    private val apiService = NetworkLayer.apiClient.marvelApiService
    private val apiClient = ApiClient(apiService)
    private val repository = CharactersRepository(apiClient = apiClient)

    private val pageListConfig: PagedList.Config = PagedList.Config.Builder()
        .setPageSize(PAGE_SIZE)
        .setPrefetchDistance(PREFETCH_DISTANCE)
        .build()

    private val dataSourceFactory = CharactersDataSourceFactory(viewModelScope, repository)
    val charactersPagedListLiveData: LiveData<PagedList<GetCharacterByIdResponse>> = LivePagedListBuilder(
        dataSourceFactory, pageListConfig).build()

}