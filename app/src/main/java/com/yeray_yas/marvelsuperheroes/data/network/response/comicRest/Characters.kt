package com.yeray_yas.marvelsuperheroes.data.network.response.comicRest

data class Characters(
    val available: Int,
    val collectionURI: String,
    val items: List<Any>,
    val returned: Int
)