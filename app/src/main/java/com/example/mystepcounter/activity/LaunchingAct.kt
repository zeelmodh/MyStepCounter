package com.example.mystepcounter.activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.work.*
import com.example.locationfetch.Fetchlocation.FetchLocation
import com.example.mystepcounter.Api.ApiCall
import com.example.mystepcounter.Api.ApiInterface
import com.example.mystepcounter.databinding.LaunchingActBinding
import com.example.syncronizedscroll.MyForeGroundService
import com.example.syncronizedscroll.workManager.WorkerClass
import kotlinx.coroutines.*
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.concurrent.TimeUnit

class LaunchingAct : AppCompatActivity(), SensorEventListener {
    lateinit var binding: LaunchingActBinding
    private val LOCATION_SETTING_REQUEST = 100
    private var sensorManager: SensorManager? = null

    var counts = 0f
    private var stepsInInt = 0

    private lateinit var fetchLocation: FetchLocation

    private fun notificationPeriodWorkManager(){
        val workRequest: PeriodicWorkRequest = PeriodicWorkRequestBuilder<WorkerClass>(15, TimeUnit.MINUTES).build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork( "", ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    private fun notificationOneTimeWorkManager(){
        val activityData = Data.Builder()
        activityData.putString("Notification_title", "Your Activity WorkManager")
        activityData.putString("walked_steps", stepsInInt.toString())
        activityData.putString("total_steps", "8000")
        activityData.putString("total_distance", getDistanceRun(counts.toLong()) + " Km")

        val workRequest : OneTimeWorkRequest  = OneTimeWorkRequestBuilder<WorkerClass>()
            .setInputData(activityData.build())
            .build()
        WorkManager.getInstance(this).enqueue(workRequest)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LaunchingActBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sensorManager =  getSystemService(Context.SENSOR_SERVICE) as SensorManager

        init()
//        init()
    }
       /* private fun init() {
            CoroutineScope(Dispatchers.Main).launch {
                init2()
            }

            CoroutineScope(Dispatchers.Main).launch {
                initCoroutine()
            }
            Log.e("INIT", "Called")

    }*/

    private fun init() {
        binding.next.setOnClickListener {
            val intent = Intent(
                this@LaunchingAct,
                Class.forName("com.example.syncronizedscroll.activity.MainActivity")
            )
            startActivity(intent)
        }

        binding.lilayout.setOnClickListener {
            if (Build.VERSION.SDK_INT < 31) {
                val serviceIntent = Intent(this, MyForeGroundService::class.java)
                serviceIntent.putExtra("Notification_title", "Your Activity Foreground")
                serviceIntent.putExtra("walked_steps", stepsInInt.toString())
                serviceIntent.putExtra("total_steps", "8000")
                serviceIntent.putExtra("total_distance", getDistanceRun(counts.toLong()) + " Km")

                applicationContext.startForegroundService(serviceIntent)
            }else{
                notificationOneTimeWorkManager()
            }
        }
    }

    fun webCall(){
        val call = ApiCall.retrofitInstance().create(ApiInterface::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val result = call.getPost()

//            Log.d("Data:", result.)
        }

    }


    /*private suspend fun init2() {
        binding.next.isEnabled = false
        delay(2000)

        binding.next.setOnClickListener {
            val intent = Intent(
                this@LaunchingAct,
                Class.forName("com.example.syncronizedscroll.MainActivity")
            )
            startActivity(intent)
        }
    }

    private suspend fun initCoroutine() {
        Log.e("No-InitCoroutine", "Called")
        binding.next.isEnabled = true
        delay(2000)
        binding.next.text = "next "
    }*/

    override fun onResume() {
        super.onResume()
        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)

//        notificationOneTimeWorkManager()
//        notificationPeriodWorkManager()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        counts = event!!.values[0]
        stepsInInt = counts.toInt()
        binding.text.text = "$stepsInInt"

        Log.e("Distance2", getDistanceRun(counts.toLong()))

        binding.walkingDistance.text = getDistanceRun(counts.toLong()) + " Km"
    }

    fun getDistanceRun(steps: Long): String {
        val distance = (steps * 76).toFloat() / 100000f
        val format = DecimalFormat("#.##")
        format.roundingMode = RoundingMode.CEILING
        return format.format(distance).toString()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            LOCATION_SETTING_REQUEST -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        startLocationUpdates()
                        Toast.makeText(
                            this@LaunchingAct,
                            "GPS is Turned on",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                    Activity.RESULT_CANCELED -> {
                        Toast.makeText(
                            this@LaunchingAct,
                            "GPS is Required to use this app",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            fetchLocation.requestPermissions()
        }
        fetchLocation.getLocation()
    }

}