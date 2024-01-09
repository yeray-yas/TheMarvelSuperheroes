package com.yeray_yas.marvelsuperheroes.data.model

data class GetCharactersPageResponse(
    val code: Int,
    val status: String,
    val data: PageData
)
