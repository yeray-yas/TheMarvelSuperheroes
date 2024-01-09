package com.yeray_yas.marvelsuperheroes.data.model

data class PageData(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<CharacterData>,
    val total: Int
)
