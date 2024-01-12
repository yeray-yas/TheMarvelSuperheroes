package com.yeray_yas.marvelsuperheroes.presentation.ui.superheroes.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.yeray_yas.marvelsuperheroes.domain.model.Character
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

class CharacterSearchViewModel : ViewModel() {



    private val _searchQuery = MutableStateFlow("")

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