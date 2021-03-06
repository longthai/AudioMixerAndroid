package com.longthai.audiomixer.respository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.longthai.audiomixer.data.Country
import com.longthai.audiomixer.network.Interface.RetrofitApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteRepository {

    fun callAPI(query:String): MutableLiveData<Country> {
        val mutableLiveData = MutableLiveData<Country>()

        RetrofitApiService().fetchData(auth = "Bearer 86vGuYu2qL37lFUioQwd2Iyh6bLcsF",query =query )
            .enqueue(object : Callback<Country> {
                override fun onFailure(call: Call<Country>, t: Throwable) {
                    Log.d("Error", "Couldn't get the data")

                }
                override fun onResponse(call: Call<Country>, response: Response<Country>) {
                    if (response.isSuccessful) {
                        mutableLiveData.postValue(response.body())
                    } else {
                        Log.d("Error", "Couldn't get the data")
                    }
                }
            })
        return mutableLiveData
    }














}