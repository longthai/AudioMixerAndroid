package com.longthai.audiomixer.ui

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.longthai.audiomixer.R
import com.longthai.audiomixer.data.MusicDetails
import kotlinx.android.synthetic.main.activity_recording.*
import zeroonezero.android.audio_mixer.AudioMixer
import zeroonezero.android.audio_mixer.AudioMixer.ProcessingListener
import zeroonezero.android.audio_mixer.input.AudioInput
import zeroonezero.android.audio_mixer.input.BlankAudioInput
import zeroonezero.android.audio_mixer.input.GeneralAudioInput


class RecordingActivity : AppCompatActivity() {



    var mProgressBar: ProgressBar? = null
    var song0:String =""
    var song1:String =""

    private lateinit var viewModel: RecordingViewModel
    var checkwhatneedSeqorPara:Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recording)


        val myList = intent.getSerializableExtra("list") as ArrayList<Int>
        Log.d("list recording","${myList[0]}")
        Log.d("list recording","${myList[1]}")

        InitilizeUI(myList[0],myList[1])
       // audio_playerSongOne.setAudioTarget("https://freesound.org/data/previews/186/186942_2594536-lq.mp3")
        startmixing.setOnClickListener {
            mProgressBar = progressBar_rec
            progressBar_rec.visibility = View.VISIBLE
            startmxingaudio()
        }
        SEQUENTIALmixing.setOnClickListener {
            checkwhatneedSeqorPara = true

        }

        PARALLELmixing.setOnClickListener {
            checkwhatneedSeqorPara  = false
        }



    }


    fun  InitilizeUI(id0:Int ,id1: Int){

        mProgressBar = progressBar_rec
        progressBar_rec.visibility = View.VISIBLE
        viewModel = ViewModelProviders.of(this).get(RecordingViewModel::class.java)

        val data = Observer<MusicDetails> {

           // progressBar_rec.visibility = View.GONE
           // Toast.makeText(this,"${it.name}", Toast.LENGTH_SHORT).show()

            Log.d("SongOne","${it.name}")

            audio_playerSongOne.setAudioTarget("${it.previews.mp3}")
            song0 ="${it.previews.mp3}"

            SecondAPIcalled(id1)
        }

        viewModel.callAPI(id0).observe(this,data)

    }

    fun SecondAPIcalled(id1: Int){

        viewModel = ViewModelProviders.of(this).get(RecordingViewModel::class.java)

        val data = Observer<MusicDetails> {

            progressBar_rec.visibility = View.GONE
           // Toast.makeText(this,"${it.name}", Toast.LENGTH_SHORT).show()
            Log.d("SongTwo","${it.name}")

            audio_playerSongTwo.setAudioTarget("${it.previews.mp3}")
            song1 ="${it.previews.mp3}"
        }

        viewModel.callAPI(id1).observe(this,data)


    }




fun startmxingaudio(){


    val progressDialog = ProgressDialog(this)
    progressDialog.setMessage("Mixing audio...")
    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
    progressDialog.setCancelable(false)
    progressDialog.isIndeterminate = false
    progressDialog.progress = 0


    val input1: AudioInput = GeneralAudioInput(song0)


    input1.volume = 0.5f //Optional


    val blankInput: AudioInput = BlankAudioInput(1000000)
    val input2: AudioInput = GeneralAudioInput(song1)
    val outputPath: String = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +"Download"+"/"+"audio_mixer_output.mp3";
    val audioMixer = AudioMixer(outputPath)
    audioMixer.addDataSource(input1)
    audioMixer.addDataSource(blankInput)
    audioMixer.addDataSource(input2)
    audioMixer.setSampleRate(44100)
    audioMixer.setBitRate(128000)
    audioMixer.setChannelCount(2)


    if(checkwhatneedSeqorPara){
        audioMixer.mixingType = AudioMixer.MixingType.PARALLEL

    }else{

        audioMixer.mixingType =  AudioMixer.MixingType.PARALLEL
    }



    audioMixer.setProcessingListener(object : ProcessingListener {
        override fun onProgress(progress: Double) {
            runOnUiThread { progressDialog.setProgress((progress * 100).toInt()) }
        }

        override fun onEnd() {
            runOnUiThread {
                Toast.makeText(applicationContext, "Success!!!", Toast.LENGTH_SHORT).show()
                audioMixer.release()
                progressBar_rec.visibility = View.GONE
                if(MainActivity.bucketRefresher!=null){
                    MainActivity.bucketRefresher.BucketRefresherlistener()
                }


            }
        }
    })
    audioMixer.start()
    //starting real processing
    audioMixer.processAsync()
 }


//Permisssion code




}// endof classs







