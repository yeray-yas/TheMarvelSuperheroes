package com.yeray_yas.marvelsuperheroes.data.network.response.comicRest

data class Data(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<Result>,
    val total: Int
)