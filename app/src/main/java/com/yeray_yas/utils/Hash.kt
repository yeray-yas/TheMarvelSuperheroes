package com.yeray_yas.utils

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Calendar

class Hash {

     private fun generateHash(ts: String, publicKey: String, privateKey: String): String {
        val input = "$ts$privateKey$publicKey"
        return try {
            val md = MessageDigest.getInstance("MD5")
            val digest = md.digest(input.toByteArray())
            digest.joinToString("") { "%02x".format(it) }
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            // Manejar la excepción apropiadamente según tus necesidades
            ""
        }
    }
}