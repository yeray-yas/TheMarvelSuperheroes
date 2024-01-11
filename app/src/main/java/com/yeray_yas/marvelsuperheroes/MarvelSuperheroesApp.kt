package com.yeray_yas.marvelsuperheroes

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class MarvelSuperheroesApp: Application() {

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}