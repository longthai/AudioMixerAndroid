package com.longthai.audiomixer.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.longthai.audiomixer.data.MusicDetails
import com.longthai.audiomixer.respository.RemoteMusicDetailRepository
import com.longthai.audiomixer.respository.RemoteRepository

class RecordingViewModel: ViewModel() {


    fun callAPI(id:Int) : MutableLiveData<MusicDetails>
    {
        return RemoteMusicDetailRepository().callAPI(id)
    }








}