package com.example.fakestore.api

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object InstanciaRetrofit {
    private val ENDERECO_DA_API = "https://valorant-api.com/"

    val api: ServicosAPI by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(ENDERECO_DA_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ServicosAPI::class.java)
    }
}