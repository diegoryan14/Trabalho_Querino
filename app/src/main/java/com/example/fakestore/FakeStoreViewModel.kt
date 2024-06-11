package com.example.fakestore

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakestore.Model.Agent
import com.example.fakestore.Model.FakeProduct
import com.example.fakestore.api.InstanciaRetrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call

class FakeStoreViewModel: ViewModel() {

    private val api = InstanciaRetrofit.api
    val agent: MutableState<List<Agent>> = mutableStateOf(emptyList())

    fun fetchAgents() {
        // Inicia uma corotina no escopo do ViewModel
        viewModelScope.launch {
            try {
                // Faz a chamada da API em uma coroutine no contexto IO
                val agentsResponse = withContext(Dispatchers.IO) {
                    api.getAllAgents()
                }
                agent.value = agentsResponse.data
            } catch (e: Exception) {
                // Tratamento de erro
                println("Erro ao buscar agentes: ${e.message}")
            }
        }
    }
}