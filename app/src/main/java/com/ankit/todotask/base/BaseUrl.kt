package com.ankit.todotask.base

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object BaseUrl {

    private const val Base_URL = "https://dummyjson.com/"

    private var retrofit: Retrofit? = null

    fun getRetrofitClient(): Retrofit {
        return retrofit ?: Retrofit.Builder().baseUrl(Base_URL)
            .addConverterFactory(GsonConverterFactory.create(getGson())).client(okHttpClient())
            .build()
    }

    private fun getGson(): Gson = GsonBuilder().setLenient().create()

    private fun loggingInterceptor() = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private fun okHttpClient() = OkHttpClient.Builder().connectTimeout(10, TimeUnit.MINUTES)
        .readTimeout(10, TimeUnit.MINUTES)
        .writeTimeout(10, TimeUnit.MINUTES).retryOnConnectionFailure(true)
        .addInterceptor(loggingInterceptor()).build()
}