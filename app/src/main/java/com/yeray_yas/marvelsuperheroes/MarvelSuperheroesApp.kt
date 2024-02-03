package com.yeray_yas.marvelsuperheroes

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
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