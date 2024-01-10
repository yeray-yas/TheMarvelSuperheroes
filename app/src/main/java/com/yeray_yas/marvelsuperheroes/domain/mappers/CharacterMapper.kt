package com.yeray_yas.marvelsuperheroes.domain.mappers

import com.yeray_yas.marvelsuperheroes.data.model.GetCharacterByIdResponse
import com.yeray_yas.marvelsuperheroes.domain.model.Character
import com.yeray_yas.marvelsuperheroes.domain.model.CharacterData
import com.yeray_yas.marvelsuperheroes.domain.model.Data

object CharacterMapper {
    fun buildFrom(response: GetCharacterByIdResponse): Character {
        val superheroDataCharacter: Data = response.data.results.getOrNull(0)?.let { result ->
            Data(
                results = listOf(
                    CharacterData(
                        comics = result.comics,
                        description = result.description,
                        events = result.events,
                        id = result.id,
                        name = result.name,
                        resourceURI = result.resourceURI,
                        series = result.series,
                        stories = result.stories,
                        thumbnail = result.thumbnail,
                        modified = result.modified,
                        urls = result.urls
                    )
                )
            )
        } ?: Data(
            results = listOf()
        )

        return Character(data = superheroDataCharacter)
    }
}


