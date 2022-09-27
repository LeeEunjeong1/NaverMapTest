package com.example.navermaptest.network

import com.example.navermaptest.model.StationResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/EvInfoServiceV2/v1/getEvSearchList")
    fun getStation(
        @Query("page") page:Int,
        @Query("perPage") perPage:Int
    ): Call<StationResponse>
}