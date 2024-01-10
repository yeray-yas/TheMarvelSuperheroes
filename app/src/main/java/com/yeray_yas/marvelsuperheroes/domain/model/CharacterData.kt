package com.yeray_yas.marvelsuperheroes.domain.model

import com.yeray_yas.marvelsuperheroes.data.model.Comics
import com.yeray_yas.marvelsuperheroes.data.model.Events
import com.yeray_yas.marvelsuperheroes.data.model.Series
import com.yeray_yas.marvelsuperheroes.data.model.Stories
import com.yeray_yas.marvelsuperheroes.data.model.Thumbnail
import com.yeray_yas.marvelsuperheroes.data.model.Url

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