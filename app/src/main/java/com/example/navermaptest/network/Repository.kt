package com.example.navermaptest.network

class Repository constructor(private val apiClient: ApiClient) {

    private val iRetrofit: ApiService? = apiClient.getClient()?.create(ApiService::class.java)
    val key = "h6tWLFJSzRclXtW8S6Bm44SwJ/cv+QLIv8xXiyPbJWfjP5X68aWML+EF/GyBg7NuoTBTWodjTyP+XtyyAB0A1Q=="
    fun getStation(page:Int,perPage:Int) = iRetrofit?.getStation(page,perPage,key)

}