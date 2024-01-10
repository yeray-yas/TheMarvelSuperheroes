package com.yeray_yas.marvelsuperheroes.domain.model.comic

data class Events(
    val available: Int,
    val collectionURI: String,
    val items: List<Any>,
    val returned: Int
)