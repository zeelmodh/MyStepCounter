package com.example.mystepcounter

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.mystepcounter.databinding.LaunchingActBinding

class LaunchingAct : AppCompatActivity(), SensorEventListener {
    lateinit var binding: LaunchingActBinding
    private val PERMISSION_CODE = 0
    private var sensorManager: SensorManager? = null

    var counts = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LaunchingActBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sensorManager =  getSystemService(Context.SENSOR_SERVICE) as SensorManager

        if (isPermissionGranted()){
            requestPermission()
        }
        init()
//        do { requestPermission() }
//        while (isPermissionGranted())

    }
    private fun init(){
        binding.next.setOnClickListener {
            val intent = Intent(this@LaunchingAct,
                Class.forName("com.example.syncronizedscroll.MainActivity"))
            startActivity(intent)

//            val intent = Intent(this@LaunchingAct, ScrollAct::class.java)
//            startActivity(intent)

//            val intent = Intent(this@LaunchingAct, ScrollViewPager::class.java)
//            startActivity(intent)

//            val intent = Intent(this@LaunchingAct, ScrollRecycleView::class.java)
//            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        counts = event!!.values[0]
        val stepsInInt = counts.toInt()
        binding.text.text = "$stepsInInt"
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
    }

    private fun requestPermission(){
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACTIVITY_RECOGNITION), PERMISSION_CODE)
    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.ACTIVITY_RECOGNITION
            ) == PackageManager.PERMISSION_GRANTED
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // STARTACTIVITY FROM HERE
                }
                else{
                    requestPermission()
                }
            }
        }
    }
}