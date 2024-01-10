package com.yeray_yas.marvelsuperheroes.domain.model.comic

data class Characters(
    val available: Int,
    val collectionURI: String,
    val items: List<Any>,
    val returned: Int
)