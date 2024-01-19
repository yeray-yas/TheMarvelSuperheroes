package com.yeray_yas.marvelsuperheroes.utils

import java.util.Calendar

object Constants {
    private const val PUBLIC_KEY = "3de6bbd5de0a40038da2c8fe677fb23b"
    private const val PRIVATE_KEY = "214c207509b1ed4c5d6456ad38a6ff91f382ead4"
    const val API_KEY = "3de6bbd5de0a40038da2c8fe677fb23b"
    val TS = Calendar.getInstance().timeInMillis
    val HASH = "$TS${PRIVATE_KEY}${PUBLIC_KEY}".md5()

    const val BASE_URL = "https://gateway.marvel.com/"

    const val PAGE_SIZE = 20
    const val PREFETCH_DISTANCE = PAGE_SIZE * 2
}