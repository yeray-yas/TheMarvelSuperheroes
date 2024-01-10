package com.yeray_yas.marvelsuperheroes.domain.model

import com.yeray_yas.marvelsuperheroes.data.model.Data

data class Character (
    val attributionHTML: String,
    val attributionText: String,
    val code: Int,
    val copyright: String,
    val data: Data,
    val etag: String,
    val status: String
)