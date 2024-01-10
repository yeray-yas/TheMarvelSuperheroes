package com.yeray_yas.marvelsuperheroes.domain.model.comic

data class Data(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<Result>,
    val total: Int
)