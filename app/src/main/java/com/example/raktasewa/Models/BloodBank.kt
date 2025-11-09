package com.example.raktasewa.Models

import com.google.gson.annotations.SerializedName

data class BloodBank(
    @SerializedName("bloodBankId")
    val bloodBankId: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("latitude")
    val latitude: Double,

    @SerializedName("longitude")
    val longitude: Double,

    @SerializedName("imageUrl")
    val imageUrl: String,

    @SerializedName("address")
    val address: String,

    @SerializedName("contact")
    val contact: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("quantity")
    val quantity: Double
)
