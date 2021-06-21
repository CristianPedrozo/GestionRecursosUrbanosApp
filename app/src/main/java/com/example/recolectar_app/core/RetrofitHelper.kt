package com.example.recolectar_app.core

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitHelper {



    fun getRetrofit(): Retrofit {

        val requestToApiInterceptor= Interceptor { chain ->

            val url =chain.request()
                .url()
                .newBuilder()
                .build()

            print(url)
            val request =chain.request()
                .newBuilder()
                .url(url.toString().replace("%3D","="))
                .build()
            print(request)

            val req2 = request
                .newBuilder()
                .url(request.url().toString().replace("%3F","?"))
                .build()

            return@Interceptor chain.proceed(req2)
        }

        val okHttpClient= OkHttpClient.Builder()
            .addInterceptor(requestToApiInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("http://46.17.108.122:1026/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }





}