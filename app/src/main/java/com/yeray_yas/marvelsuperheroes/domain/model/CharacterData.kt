package com.yeray_yas.marvelsuperheroes.domain.model

import com.yeray_yas.marvelsuperheroes.data.network.response.characterRest.Comics
import com.yeray_yas.marvelsuperheroes.data.network.response.characterRest.Events
import com.yeray_yas.marvelsuperheroes.data.network.response.characterRest.Series
import com.yeray_yas.marvelsuperheroes.data.network.response.characterRest.Stories
import com.yeray_yas.marvelsuperheroes.data.network.response.characterRest.Thumbnail
import com.yeray_yas.marvelsuperheroes.data.network.response.characterRest.Url

data class CharacterData(
    val comics: Comics,
    val description: String,
    val events: Events,
    val id: Int,
    val modified: String,
    val name: String,
    val resourceURI: String,
    val series: Series,
    val stories: Stories,
    val thumbnail: Thumbnail,
    val urls: List<Url>
)