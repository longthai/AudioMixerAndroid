package com.longthai.audiomixer.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.longthai.audiomixer.data.Country
import com.longthai.audiomixer.respository.RemoteRepository

class MainViewModel : ViewModel() {

    fun callAPI(query:String) : MutableLiveData<Country>
    {
        return RemoteRepository().callAPI(query)
    }



}