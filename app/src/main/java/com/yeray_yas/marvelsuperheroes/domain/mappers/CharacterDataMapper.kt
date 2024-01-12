package com.yeray_yas.marvelsuperheroes.domain.mappers

import com.yeray_yas.marvelsuperheroes.data.network.response.characterRest.CharacterData
import com.yeray_yas.marvelsuperheroes.data.network.response.characterRest.Data
import com.yeray_yas.marvelsuperheroes.data.network.response.GetCharacterByIdResponse
import com.yeray_yas.marvelsuperheroes.domain.model.Character

object CharacterDataMapper {

    fun CharacterData.toGetCharacterByIdResponse(): GetCharacterByIdResponse {
        return GetCharacterByIdResponse(
            attributionHTML = "",
            attributionText = "",
            code = 0,
            copyright = "",
            data = Data(results = listOf(this)),
            etag = "",
            status = ""
        )
    }

/*    // Mapeador de CharacterData a Character en la capa de dominio
    fun CharacterData.toCharacter(): Character {
        return Character(
            // Mapea aquí los campos necesarios
            data = com.yeray_yas.marvelsuperheroes.domain.model.Data(results = listOf(this))
        )
    }

    // Otra función si necesitas mapear una lista de CharacterData
    fun List<CharacterData>.toCharacterList(): List<Character> {
        return this.map { it.toCharacter() }
    }*/

    fun CharacterData.toCharacter(): Character {
        return Character(
            data = com.yeray_yas.marvelsuperheroes.domain.model.Data(results = listOf(this.toDomain()))
        )
    }

    fun Data.toDomain(): com.yeray_yas.marvelsuperheroes.domain.model.Data {
        return com.yeray_yas.marvelsuperheroes.domain.model.Data(
            results = this.results.map { it.toDomain() }
        )
    }

    private fun CharacterData.toDomain(): com.yeray_yas.marvelsuperheroes.domain.model.CharacterData {
        return com.yeray_yas.marvelsuperheroes.domain.model.CharacterData(
            comics,
            description,
            events, id, modified, name, resourceURI, series, stories, thumbnail, urls
        )
    }
}