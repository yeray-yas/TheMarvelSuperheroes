package com.yeray_yas.marvelsuperheroes.domain.model.comic

data class ComicCharacter(
    val attributionHTML: String,
    val attributionText: String,
    val code: Int,
    val copyright: String,
    val `data`: Data,
    val etag: String,
    val status: String
)