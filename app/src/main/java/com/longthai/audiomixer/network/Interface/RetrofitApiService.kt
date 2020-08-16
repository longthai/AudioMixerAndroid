package com.longthai.audiomixer.network.Interface

import com.longthai.audiomixer.data.Country
import com.longthai.audiomixer.data.MusicDetails
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitApiService {
//search/text/?query=piano
    @GET("search/text/")
    fun fetchData(@Header("Authorization") auth:String,
    @Query(value="query") query:String): Call<Country>

    @GET("sounds/{id}/")
    fun fetchDataDetails(@Header("Authorization") auth:String
                         ,@Path("id")id:Int): Call<MusicDetails>







    companion object {
        operator fun invoke(): RetrofitApiService {
            return Retrofit.Builder()
                .baseUrl("https://freesound.org/apiv2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitApiService::class.java)
        }
    }




}