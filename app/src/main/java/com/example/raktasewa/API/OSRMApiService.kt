package com.example.raktasewa.API

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

// OSRM Response data models
data class OSRMResponse(
    val code: String,
    val routes: List<OSRMRoute>?
)

data class OSRMRoute(
    val geometry: OSRMGeometry,
    val distance: Double, // in meters
    val duration: Double  // in seconds
)

data class OSRMGeometry(
    val coordinates: List<List<Double>> // [[lon, lat], [lon, lat], ...]
)

interface OSRMApiService {
    // OSRM Demo Server - Free to use
    @GET("route/v1/driving/{coordinates}")
    suspend fun getRoute(
        @Path("coordinates") coordinates: String // format: "lon1,lat1;lon2,lat2"
    ): Response<OSRMResponse>
}
