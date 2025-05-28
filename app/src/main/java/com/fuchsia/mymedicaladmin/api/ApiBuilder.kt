package com.fuchsia.mymedicaladmin.api

import com.fuchsia.mymedicaladmin.constant.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiBuilder{

    val api: ApiService =
        Retrofit.Builder().client(OkHttpClient.Builder().build()).baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build().create(ApiService::class.java)
}