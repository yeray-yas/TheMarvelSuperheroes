package com.yeray_yas.marvelsuperheroes.di

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.yeray_yas.marvelsuperheroes.MarvelSuperheroesApp
import com.yeray_yas.marvelsuperheroes.data.network.remote.ApiClient
import com.yeray_yas.marvelsuperheroes.data.network.remote.MarvelApiService
import com.yeray_yas.marvelsuperheroes.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(getLoggingHttpClient())
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideMarvelApiService(retrofit: Retrofit): MarvelApiService {
        return retrofit.create(MarvelApiService::class.java)
    }


 /*   private val marvelApiService: MarvelApiService by lazy {
        NetworkLayer.retrofit.create(MarvelApiService::class.java)
    }*/

    /*val apiClient = ApiClient(marvelApiService)*/

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