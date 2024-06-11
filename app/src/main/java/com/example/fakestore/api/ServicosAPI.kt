package com.example.fakestore.api

import com.example.fakestore.Model.Agent
import com.example.fakestore.Model.AgentsResponse
import com.example.fakestore.Model.FakeProduct
import retrofit2.Call
import retrofit2.http.GET

interface ServicosAPI {
    
    @GET("v1/agents?language=pt-BR")
    suspend fun getAllAgents(): AgentsResponse

}