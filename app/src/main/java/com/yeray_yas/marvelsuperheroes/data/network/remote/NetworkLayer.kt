package com.yeray_yas.marvelsuperheroes.data.network.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkLayer {
    private const val BASE_URL = "https://gateway.marvel.com/"

     private val retrofit: Retrofit =  Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val marvelApiService:MarvelApiService by lazy {
        retrofit.create(MarvelApiService::class.java)
    }

    val apiClient = ApiClient(marvelApiService)

}