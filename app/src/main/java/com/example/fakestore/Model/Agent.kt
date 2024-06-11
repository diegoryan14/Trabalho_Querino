package com.example.fakestore.Model

data class AgentsResponse(
    val data: List<Agent>
)

data class Agent(
    val id: String,
    val displayName: String,
    val description: String,
    val abilities: List<Ability>,
    val displayIconSmall: String,
    val background: String
)