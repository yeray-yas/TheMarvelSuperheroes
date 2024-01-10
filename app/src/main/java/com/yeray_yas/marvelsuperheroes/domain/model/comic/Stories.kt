package com.yeray_yas.marvelsuperheroes.domain.model.comic

data class Stories(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemX>,
    val returned: Int
)