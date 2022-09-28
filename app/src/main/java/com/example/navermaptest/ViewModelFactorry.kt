package com.example.navermaptest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.navermaptest.network.Repository


class ViewModelFactory constructor(private val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            MainViewModel(this.repository) as T
        }else{
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}