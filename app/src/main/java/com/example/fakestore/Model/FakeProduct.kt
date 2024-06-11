package com.example.fakestore.Model

import android.accounts.AuthenticatorDescription
import kotlinx.serialization.Serializable

@Serializable
data class FakeProduct(
    val title: String,
    val description: String,
    val price: Double,
    val image: String
)
