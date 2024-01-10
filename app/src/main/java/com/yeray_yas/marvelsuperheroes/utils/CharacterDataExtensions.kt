package com.yeray_yas.marvelsuperheroes.utils

import com.yeray_yas.marvelsuperheroes.data.model.CharacterData
import com.yeray_yas.marvelsuperheroes.data.model.Data
import com.yeray_yas.marvelsuperheroes.data.model.GetCharacterByIdResponse

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