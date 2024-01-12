package com.yeray_yas.marvelsuperheroes.presentation.superheroes.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeray_yas.marvelsuperheroes.data.repository.SharedRepository
import com.yeray_yas.marvelsuperheroes.domain.model.Character
import com.yeray_yas.marvelsuperheroes.utils.MarvelCache
import kotlinx.coroutines.launch

class CharacterDetailViewModel : ViewModel() {

    private val repository = SharedRepository()

    private val _characterByIdLiveData = MutableLiveData<Character?>()
    val characterByIdLiveData: LiveData<Character?> = _characterByIdLiveData

    fun refreshCharacter(characterId: Int) {

        //Check the cache for our character
        val cachedSuperheroCharacter = MarvelCache.characterMap[characterId]
        if (cachedSuperheroCharacter != null) {
            _characterByIdLiveData.postValue(cachedSuperheroCharacter)
            return
        }

        // Otherwise, we need to make the network call for the character
        viewModelScope.launch {
            val response = repository.getCharacterById(characterId)

            _characterByIdLiveData.postValue(response)

            // Update cache if non-null char received
            response?.let {
                MarvelCache.characterMap[characterId] = it
            }
        }
    }
}