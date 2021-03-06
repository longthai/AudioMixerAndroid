package com.longthai.audiomixer.respository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.longthai.audiomixer.data.MusicDetails
import com.longthai.audiomixer.network.Interface.RetrofitApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteMusicDetailRepository {


    fun callAPI(id: Int): MutableLiveData<MusicDetails> {
        val mutableLiveData = MutableLiveData<MusicDetails>()

        RetrofitApiService().fetchDataDetails(
            auth = "Bearer 86vGuYu2qL37lFUioQwd2Iyh6bLcsF",
            id = id
        )
            .enqueue(object : Callback<MusicDetails> {
                override fun onFailure(call: Call<MusicDetails>, t: Throwable) {
                    Log.d("Error", "Couldn't get the data")

                }

                override fun onResponse(
                    call: Call<MusicDetails>,
                    response: Response<MusicDetails>
                ) {
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