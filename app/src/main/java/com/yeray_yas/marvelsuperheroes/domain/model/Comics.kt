package com.yeray_yas.marvelsuperheroes.domain.model

data class Comics(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)