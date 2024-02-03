package com.yeray_yas.marvelsuperheroes.domain.model

import com.google.gson.annotations.SerializedName

data class Character (
    @SerializedName("data")
    val data: Data
)