package com.yeray_yas.marvelsuperheroes.data.model

data class Events(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)