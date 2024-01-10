package com.yeray_yas.marvelsuperheroes.domain.model.comic

data class Creators(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)