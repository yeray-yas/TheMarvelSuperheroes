package com.yeray_yas.marvelsuperheroes.utils

import com.yeray_yas.marvelsuperheroes.BuildConfig
import java.util.Calendar

object Constants {
    private const val PUBLIC_KEY = BuildConfig.PUBLIC_KEY
    private const val PRIVATE_KEY = BuildConfig.PRIVATE_KEY
    const val API_KEY = PUBLIC_KEY
    val TS = Calendar.getInstance().timeInMillis
    val HASH = Hash().generateHash(TS, PUBLIC_KEY, PRIVATE_KEY)

    const val BASE_URL = BuildConfig.BASE_URL

    const val PAGE_SIZE = 20
    const val PREFETCH_DISTANCE = PAGE_SIZE * 2
}