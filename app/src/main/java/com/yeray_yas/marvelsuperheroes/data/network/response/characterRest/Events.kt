package com.yeray_yas.marvelsuperheroes.data.network.response.characterRest

data class Events(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)