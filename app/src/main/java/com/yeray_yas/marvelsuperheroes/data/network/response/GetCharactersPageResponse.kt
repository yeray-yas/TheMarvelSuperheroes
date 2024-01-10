package com.yeray_yas.marvelsuperheroes.data.network.response

import com.yeray_yas.marvelsuperheroes.data.network.response.characterRest.DataPaging

data class GetCharactersPageResponse(
    val code: Int,
    val data: DataPaging
)
