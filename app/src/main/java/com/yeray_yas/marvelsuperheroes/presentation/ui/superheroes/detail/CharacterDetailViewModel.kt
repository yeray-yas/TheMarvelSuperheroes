package com.yeray_yas.marvelsuperheroes.presentation.ui.superheroes.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeray_yas.marvelsuperheroes.data.repository.CharactersRepository
import com.yeray_yas.marvelsuperheroes.domain.model.Character
import com.yeray_yas.marvelsuperheroes.utils.ApiCredentialsManager
import kotlinx.coroutines.launch

class CharacterDetailViewModel : ViewModel() {

    private val repository = CharactersRepository()

    private val _characterByIdLiveData = MutableLiveData<Character?>()
    val characterByIdLiveData: LiveData<Character?> = _characterByIdLiveData

    fun refreshCharacter(
        characterId: Int,
        apiKey: String = ApiCredentialsManager.getApiKey(),
        hash: String = ApiCredentialsManager.getHash(ApiCredentialsManager.getTs()),
        ts: Long = ApiCredentialsManager.getTs()
    ) = viewModelScope.launch {
        val character = repository.getCharacterById(characterId, apiKey, hash, ts)
        _characterByIdLiveData.postValue(character)
    }
}