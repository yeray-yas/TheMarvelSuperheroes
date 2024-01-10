package com.yeray_yas.utils

import java.util.Calendar

object Constants {
    const val PUBLIC_KEY = "3de6bbd5de0a40038da2c8fe677fb23b"
    const val PRIVATE_KEY = "214c207509b1ed4c5d6456ad38a6ff91f382ead4"
    val TS = Calendar.getInstance().timeInMillis.toString()

    const val PAGE_SIZE = 20
    const val PREFETCH_DISTANCE = PAGE_SIZE * 2

    const val INTENT_EXTRA_CHARACTER_ID = "character_id_extra"
}