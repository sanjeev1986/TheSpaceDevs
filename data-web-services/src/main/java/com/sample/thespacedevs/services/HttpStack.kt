package com.sample.thespacedevs.services

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Generic HTTP stack boiler plate
 */
@Singleton
class HttpStack(
    private val baseUrl: String,
    private val gson: Gson
) {
    private val okhttpClient: OkHttpClient by lazy {
        val builder = OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        builder.build()
    }

    private val retrofit: Retrofit by lazy {
        val retrofitBuilder = Retrofit.Builder()
        retrofitBuilder
            .baseUrl(baseUrl)
            .client(okhttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun <T> create(cls: Class<T>): T = retrofit.create(cls)
}
