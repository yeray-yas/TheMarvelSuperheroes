package com.yeray_yas.marvelsuperheroes.data.network.response.characterRest

data class DataPaging(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<CharacterData>,
    val total: Int
)
