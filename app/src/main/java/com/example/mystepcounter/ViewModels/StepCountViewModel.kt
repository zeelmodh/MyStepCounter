package com.example.mystepcounter.ViewModels

import android.Manifest
import android.app.Activity
import android.hardware.Sensor
import android.hardware.SensorManager
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.os.Build
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StepCountViewModel: ViewModel(), SensorEventListener {
    private var sensorManager: SensorManager? = null

    val stepCount: MutableLiveData<Float> by lazy {
        MutableLiveData<Float>()
    }

    fun askPermission(context: Context){
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACTIVITY_RECOGNITION)
            != PackageManager.PERMISSION_GRANTED) {
            requestPermission(context)
        }else{
            stepCounter(context)
        }
    }

    private fun requestPermission(context: Context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(context as Activity,
                arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
                100)
        }else{
            Toast.makeText(context, "Device doesn't support physical activity tracking", Toast.LENGTH_SHORT).show()
        }
    }

    private fun stepCounter(context: Context){
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val counts = event!!.values[0]
//        val stepsInInt = counts.toLong()
        stepCount.value = counts
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) { }

    override fun onCleared() {
        super.onCleared()
        sensorManager?.unregisterListener(this)
    }
}