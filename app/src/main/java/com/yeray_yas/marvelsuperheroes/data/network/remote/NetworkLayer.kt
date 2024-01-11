package com.yeray_yas.marvelsuperheroes.data.network.remote

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.yeray_yas.marvelsuperheroes.MarvelSuperheroesApp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkLayer {
    private const val BASE_URL = "https://gateway.marvel.com/"


    private val retrofit: Retrofit = Retrofit.Builder()
        .client(getLoggingHttpClient())
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val marvelApiService: MarvelApiService by lazy {
        retrofit.create(MarvelApiService::class.java)
    }

    val apiClient = ApiClient(marvelApiService)

    private fun getLoggingHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })

        builder.addInterceptor(
            ChuckerInterceptor.Builder(MarvelSuperheroesApp.context)
                .collector(ChuckerCollector(MarvelSuperheroesApp.context))
                .maxContentLength(250000L)
                .redactHeaders(emptySet())
                .alwaysReadResponseBody(false)
                .build()
        )

        return builder.build()
    }

}