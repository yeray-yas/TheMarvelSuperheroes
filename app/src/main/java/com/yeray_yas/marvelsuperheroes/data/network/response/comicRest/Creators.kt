package com.yeray_yas.marvelsuperheroes.data.network.response.comicRest

data class Creators(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)