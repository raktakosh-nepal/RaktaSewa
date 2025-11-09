package com.example.raktasewa.API

import com.example.raktasewa.Models.BloodBank
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BloodBankApiService {
    @GET("getbloodbanks/{latitude}/{longitude}/{type}")
    suspend fun getBloodBanks(
        @Path("latitude") latitude: Double,
        @Path("longitude") longitude: Double,
        @Path("type") type: String
    ): Response<List<BloodBank>>
}
