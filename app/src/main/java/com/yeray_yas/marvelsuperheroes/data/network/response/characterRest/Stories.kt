package com.yeray_yas.marvelsuperheroes.data.network.response.characterRest

data class Stories(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemXXX>,
    val returned: Int
)