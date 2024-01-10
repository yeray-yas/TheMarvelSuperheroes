package com.yeray_yas.marvelsuperheroes.domain.mappers

import com.yeray_yas.marvelsuperheroes.data.network.response.characterRest.CharacterData
import com.yeray_yas.marvelsuperheroes.data.network.response.characterRest.Data
import com.yeray_yas.marvelsuperheroes.data.network.response.GetCharacterByIdResponse

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
}