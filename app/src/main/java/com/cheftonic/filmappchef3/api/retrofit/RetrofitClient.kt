package com.cheftonic.filmappchef3.api.retrofit

import com.cheftonic.filmappchef3.api.api.TmdbService
import com.cheftonic.filmappchef3.util.Constantes
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private val interceptor = Interceptor { interceptor ->
        val originalRequest = interceptor.request()
        val newRequest = originalRequest.newBuilder()
            .addHeader("accept", "application/json")
            .addHeader(Constantes.AUTHORIZATION, Constantes.BEARER_TOKEN)
            .build()
        interceptor.proceed(newRequest)
    }
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constantes.BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val tmdbService: TmdbService by lazy {
        retrofit.create(TmdbService::class.java)
    }
}