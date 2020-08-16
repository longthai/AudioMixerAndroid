package com.longthai.audiomixer.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.longthai.audiomixer.R
import com.longthai.audiomixer.data.MusicDetails
import kotlinx.android.synthetic.main.activity_recording.*

class PlayingActivity : AppCompatActivity() {




    var mProgressBar: ProgressBar? = null
    var song0:String =""
    private lateinit var viewModel: PlayingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playing)

        val myList = intent.getSerializableExtra("id") as Int
        Log.d("list recording","${myList}")
        InitilizeUI(myList)
    }


    fun  InitilizeUI(id0:Int ){

        mProgressBar = progressBar_rec
        progressBar_rec.visibility = View.VISIBLE
        viewModel = ViewModelProviders.of(this).get(PlayingViewModel::class.java)

        val data = Observer<MusicDetails> {

            // progressBar_rec.visibility = View.GONE
            // Toast.makeText(this,"${it.name}", Toast.LENGTH_SHORT).show()

            Log.d("SongOne","${it.name}")
            Log.d("Songone","${it.url}")
            Log.d("Songone","${it.previews.mp3}")

            audio_playerSongOne.setAudioTarget("${it.previews.mp3}")
            song0 ="${it.previews.mp3}"

            progressBar_rec.visibility = View.GONE
        }

        viewModel.callAPI(id0).observe(this,data)

    }

}