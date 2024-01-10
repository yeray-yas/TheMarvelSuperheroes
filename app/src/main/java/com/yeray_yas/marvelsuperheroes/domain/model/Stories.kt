package com.yeray_yas.marvelsuperheroes.domain.model

data class Stories(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemXXX>,
    val returned: Int
)