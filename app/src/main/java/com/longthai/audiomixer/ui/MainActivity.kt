package com.longthai.audiomixer.ui

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.longthai.audiomixer.R
import com.longthai.audiomixer.`interface`.BucketRefresher
import com.longthai.audiomixer.`interface`.MusicCounterListener
import com.longthai.audiomixer.adapter.ModelListAdapter
import com.longthai.audiomixer.data.Country
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() , MusicCounterListener,BucketRefresher {


    var mProgressBar: ProgressBar? = null
    private lateinit var viewModel: MainViewModel
    val REQUEST_ID_MULTIPLE_PERMISSIONS = 7
    var query:String = "scream"
    var countermusic:Int = 0
    val Selectionlist = ArrayList<Int>()
     companion object{
        lateinit var bucketRefresher:BucketRefresher
     }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkAndroidVersion()
        InitilizeUI()
        searchbaricon.setOnClickListener {

            query = searchid.text.toString()
            InitilizeUI()
        }

        bucketRefresher = this

    }

    fun  InitilizeUI(){
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        mProgressBar = progressBar
        progressBar.visibility = View.VISIBLE
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        val data = Observer<Country> {
            recyclerview.adapter = ModelListAdapter(it.results,this)
            progressBar.visibility = View.GONE
            Toast.makeText(this,"${it.count}",Toast.LENGTH_SHORT).show()
        }

        viewModel.callAPI(query).observe(this,data)

    }
//permission code ...


    private fun checkAndroidVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkAndRequestPermissions()
        } else {
            // code for lollipop and pre-lollipop devices

            Toast.makeText(applicationContext,"Your Mobile is not Suported",Toast.LENGTH_LONG).show()
        }
    }
    private fun checkAndRequestPermissions(): Boolean {
        val camera = ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.CAMERA
        )
        val wtite = ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val read = ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        if (wtite != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA)
        }
        if (read != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                listPermissionsNeeded.toTypedArray(),
                REQUEST_ID_MULTIPLE_PERMISSIONS
            )
            return false
        }
        return true
    }

///////////


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        Log.d("in fragment on request", "Permission callback called-------")
        when (requestCode) {
            REQUEST_ID_MULTIPLE_PERMISSIONS -> {
                val perms: MutableMap<String, Int> = HashMap()
                // Initialize the map with both permissions
                perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] =
                    PackageManager.PERMISSION_GRANTED
                perms[Manifest.permission.CAMERA] = PackageManager.PERMISSION_GRANTED
                perms[Manifest.permission.READ_EXTERNAL_STORAGE] = PackageManager.PERMISSION_GRANTED
                // Fill with actual results from user
                if (grantResults.size > 0) {
                    var i = 0
                    while (i < permissions.size) {
                        perms[permissions[i]] = grantResults[i]
                        i++
                    }
                    // Check for both permissions
                    if (perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED && perms[Manifest.permission.CAMERA] == PackageManager.PERMISSION_GRANTED && perms[Manifest.permission.READ_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED
                    ) {
                        Log.d(
                            "in fragment on request",
                            "CAMERA & WRITE_EXTERNAL_STORAGE READ_EXTERNAL_STORAGE permission granted"
                        )
                        // process the normal flow
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d(
                            "in fragment on request",
                            "Some permissions are not granted ask again "
                        )
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                            ) || ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                Manifest.permission.CAMERA
                            ) || ActivityCompat.shouldShowRequestPermissionRationale(
                               this,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            )
                        ) {
                            showDialogOK("Camera and Storage Permission required for this app",
                                DialogInterface.OnClickListener { dialog, which ->
                                    when (which) {
                                        DialogInterface.BUTTON_POSITIVE -> checkAndRequestPermissions()
                                        DialogInterface.BUTTON_NEGATIVE -> {
                                        }
                                    }
                                })
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Go to settings and enable permissions",
                                Toast.LENGTH_LONG
                            )
                                .show()
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }
    }


    private fun showDialogOK(
        message: String,
        okListener: DialogInterface.OnClickListener
    ) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", okListener)
            .create()
            .show()
    }

    override fun callback(listener: Boolean,musicId:Int) {
        if(listener){
            countermusic++
            musiccounter.text= "${countermusic}"
            Selectionlist.add(musicId)
            Log.d("ArrayList MainClass","$Selectionlist")
            if(Selectionlist.size.equals(2)){
                  Log.d("Navigate" ,"Called totalclicks in Two")

                  val intent = Intent(this, RecordingActivity::class.java).apply {
                        this.putExtra("list",Selectionlist)
                    }
                this.startActivity(intent)
            }
            if(Selectionlist.size > 2){
                Toast.makeText(this,"You need to select atleast  two musics for same or diffrent categories for proceeding..",Toast.LENGTH_SHORT).show()
            }


        }else{
            countermusic--
            musiccounter.text = "${countermusic}"
            Selectionlist.remove(musicId)
            Log.d("Array MainClass","$Selectionlist")

            if(Selectionlist.size.equals(2)){
                Log.d("Navigate" ,"Called totalclicks in Two")

                val intent = Intent(this, RecordingActivity::class.java).apply {
                    this.putExtra("list",Selectionlist)
                }
                this.startActivity(intent)
            }


        }

    }

    override fun BucketRefresherlistener() {
        countermusic = 0
        musiccounter.text = "${countermusic}"
        Selectionlist.clear()
        InitilizeUI()

    }

}//end of classs
