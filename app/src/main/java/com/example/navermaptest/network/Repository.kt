package com.example.navermaptest.network

class Repository constructor(private val retrofitClient: RetrofitClient) {

    private val iRetrofit: ApiService? = retrofitClient.getClient()?.create(ApiService::class.java)

    fun getStation(page:Int,perPage:Int) = iRetrofit?.getStation(page,perPage)

}