package com.yeray_yas.marvelsuperheroes.data.model

data class GetCharacterByIdResponse(
    val attributionHTML: String,
    val attributionText: String,
    val code: Int,
    val copyright: String,
    val data: Data,
    val etag: String,
    val status: String
)