package ru.magomedcoder.askue.utils

import okhttp3.OkHttpClient
import okhttp3.Request

class NetworkHelper()  {

    fun addHeaderAuthorization(): OkHttpClient {
        val token = "64c804a1b1ef15cbd19dc76eb616d3a21d8fa811"
        return OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization", "Token $token")
                .build()
            chain.proceed(newRequest)
        }.build()
    }

}