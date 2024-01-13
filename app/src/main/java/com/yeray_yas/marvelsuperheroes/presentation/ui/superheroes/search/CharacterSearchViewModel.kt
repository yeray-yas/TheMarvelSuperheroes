package com.yeray_yas.marvelsuperheroes.presentation.ui.superheroes.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.yeray_yas.marvelsuperheroes.data.pagination.character.search.CharacterSearchPagingSource
import com.yeray_yas.marvelsuperheroes.utils.Constants.PAGE_SIZE
import com.yeray_yas.marvelsuperheroes.utils.Constants.PREFETCH_DISTANCE
import com.yeray_yas.marvelsuperheroes.utils.Event

class CharacterSearchViewModel : ViewModel() {

    private var currentUserSearch: String? = ""

    private var pagingSource: CharacterSearchPagingSource? = null
        get() {
            if (field == null || field?.invalid == true){
                field = currentUserSearch?.let {
                    CharacterSearchPagingSource(it){ localException ->
                        // Notify our LiveData of an issue from the PagingSource
                       _localExceptionEventLiveData.postValue(Event(localException))
                    }
                }
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

    // For error handling propagation
    private val _localExceptionEventLiveData = MutableLiveData<Event<CharacterSearchPagingSource.LocalException>>()
    val localExceptionEventLiveData: LiveData<Event<CharacterSearchPagingSource.LocalException>> = _localExceptionEventLiveData

    fun submitQuery(userSearch: String?) {
        currentUserSearch = userSearch
        pagingSource?.invalidate()
    }
}