package com.yeray_yas.marvelsuperheroes.data.network.response

import com.yeray_yas.marvelsuperheroes.data.network.response.comicRest.Data

data class GetComicByIdResponse(
    val attributionHTML: String,
    val attributionText: String,
    val code: Int,
    val copyright: String,
    val `data`: Data,
    val etag: String,
    val status: String
)