package com.yeray_yas.marvelsuperheroes.data.network.response.comicRest

data class Stories(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemX>,
    val returned: Int
)