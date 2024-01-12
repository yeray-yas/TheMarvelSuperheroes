package com.yeray_yas.marvelsuperheroes.presentation.ui.superheroes.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yeray_yas.marvelsuperheroes.domain.model.Character
import com.yeray_yas.marvelsuperheroes.utils.Constants.PAGE_SIZE
import com.yeray_yas.marvelsuperheroes.utils.Constants.PREFETCH_DISTANCE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

class CharacterSearchViewModel : ViewModel() {
    /*private val _searchQuery = MutableStateFlow("")*/
    private var currentUserSearch: String? = ""
    private var pagingSource: CharacterSearchPagingSource? = null
        get() {
            if (field == null || field?.invalid == true){
                field = currentUserSearch?.let { CharacterSearchPagingSource(it) }
            }

            return field
        }

    val flow = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance
        PagingConfig(
            pageSize = PAGE_SIZE,
            prefetchDistance = PREFETCH_DISTANCE,
            enablePlaceholders = false
        )
    ) {
        pagingSource!!
    }.flow.cachedIn(viewModelScope)

    fun submitQuery(userSearch: String?) {
        currentUserSearch = userSearch
        pagingSource?.invalidate()
    }

    /*    @OptIn(ExperimentalCoroutinesApi::class)
        private val searchSuperheroes: Flow<PagingData<Character>> = _searchQuery
            .flatMapLatest { query ->
                if (query.isBlank()) {
                    *//*getSuperheroesUseCase.execute()*//*
            } else {
               *//* searchSuperheroesUseCase.execute(query)*//*
            }
        }
        .cachedIn(viewModelScope)
    val superheroes: Flow<PagingData<Character>> = searchSuperheroes
    suspend fun setSearchQuery(query: String) {
        _searchQuery.emit(query)
    }*/
    // TODO: Implement the ViewModel
}