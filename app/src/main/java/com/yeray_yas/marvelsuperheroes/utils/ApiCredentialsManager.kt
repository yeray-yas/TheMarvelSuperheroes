package com.yeray_yas.marvelsuperheroes.utils

import java.util.Calendar

object ApiCredentialsManager {
    private var cachedApiKey: String? = null
    private var cachedHash: String? = null
    private var cachedTs: Long = 0

    fun getApiKey(): String {
        if (cachedApiKey == null) {
            cachedApiKey = Constants.API_KEY
        }
        return cachedApiKey!!
    }

    fun getHash(ts: Long): String {
        if (cachedHash == null || cachedTs != ts) {
            cachedHash = "$ts${Constants.PRIVATE_KEY}${Constants.PUBLIC_KEY}".md5()
            cachedTs = ts
        }
        return cachedHash!!
    }

    fun getTs(): Long {
        if (cachedTs == 0L) {
            cachedTs = Calendar.getInstance().timeInMillis
        }
        return cachedTs
    }
}
