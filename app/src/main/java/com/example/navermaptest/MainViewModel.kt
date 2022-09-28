package com.example.navermaptest

import android.app.Application
import androidx.lifecycle.*
import com.example.navermaptest.model.Station
import com.example.navermaptest.model.StationResponse
import com.example.navermaptest.network.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okio.ByteString
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository:Repository):ViewModel() {
    private val _isSuccess: MutableLiveData<List<Station>> = MutableLiveData()
    val isSuccess: LiveData<List<Station>> get() = _isSuccess

    private val _isError = MutableLiveData<String>()
    val isError: LiveData<String> get() = _isError


    fun getStation(page:Int, perPage:Int){
        CoroutineScope(Dispatchers.Default).launch {
            try{
                val response = repository.getStation(page,perPage)
                response?.enqueue(object : Callback<StationResponse>{
                    override fun onFailure(call: Call<StationResponse>, t: Throwable) {
                        _isError.postValue("error")
                    }

                    override fun onResponse(call: Call<StationResponse>,response: Response<StationResponse>) {
                        val res = response.body()?.data
                        _isSuccess.postValue(res)
                    }
                })
            } catch(e: Exception){
                _isError.postValue(e.message)
            }
        }

    }

}