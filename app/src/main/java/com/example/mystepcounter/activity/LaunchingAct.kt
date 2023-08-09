package com.example.mystepcounter.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.*
import com.example.mystepcounter.Api.ApiCall
import com.example.mystepcounter.Api.ApiInterface
import com.example.mystepcounter.ViewModels.StepCountViewModel
import com.example.mystepcounter.databinding.LaunchingActBinding
import com.example.syncronizedscroll.MyForeGroundService
import com.example.syncronizedscroll.workManager.WorkerClass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.concurrent.TimeUnit

//class LaunchingAct : AppCompatActivity(), SensorEventListener {
class LaunchingAct : AppCompatActivity() {
    lateinit var binding: LaunchingActBinding

    private val stepCountViewModel: StepCountViewModel by viewModels()

    var stepCountLong: Long?= null
    var stepCountInt: Int?= null
    var stepCountString: String?= null

    private fun notificationPeriodWorkManager(){
        val workRequest: PeriodicWorkRequest = PeriodicWorkRequestBuilder<WorkerClass>(15, TimeUnit.MINUTES).build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork( "", ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    private fun notificationOneTimeWorkManager(l: Long) {
        stepCountInt = stepCountViewModel.stepCount.value?.toInt()
        val activityData = Data.Builder()
        activityData.putString("Notification_title", "Your Activity Assistant From Step Counter App")
        activityData.putString("walked_steps", stepCountString)
        activityData.putString("total_steps", "8000")
        activityData.putString("total_distance", getDistanceRun(l) + " Km")

        val workRequest : OneTimeWorkRequest  = OneTimeWorkRequestBuilder<WorkerClass>()
            .setInputData(activityData.build())
            .build()
        WorkManager.getInstance(this).enqueue(workRequest)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LaunchingActBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initStepLiveData()
        init()
    }

    @SuppressLint("SetTextI18n")
    private fun initStepLiveData() {
        stepCountViewModel.askPermission(this)
        // add to shared pref. when app launches and sensor has not changed.
        val stepObserver = Observer<Float> { stepCount->
            stepCountLong = stepCount.toLong()
            stepCountInt = stepCount.toInt()
            stepCountString = stepCountInt.toString()
            binding.text.text = stepCountString
            binding.walkingDistance.text = "${getDistanceRun(stepCount.toLong())} Km"
            Log.e("converts",
                "Float coming Type: $stepCount  Long: $stepCountLong  Int: $stepCountInt  String: $stepCountString"
            )
        }
        stepCountViewModel.stepCount.observe(this, stepObserver)
    }

    private fun init() {
        binding.next.setOnClickListener {
            val intent = Intent(
                this@LaunchingAct,
                Class.forName("com.example.syncronizedscroll.activity.MainActivity")
            )
            startActivity(intent)
        }

        binding.lilayout.setOnClickListener {
            stepCountLong?.let {
                if (Build.VERSION.SDK_INT < 31) {
                    val serviceIntent = Intent(this, MyForeGroundService::class.java)
                    serviceIntent.putExtra("Notification_title", "Your Activity Foreground")
                    serviceIntent.putExtra("walked_steps", stepCountString)
                    serviceIntent.putExtra("total_steps", "8000")
                    serviceIntent.putExtra("total_distance", getDistanceRun(it) + " Km")

                    applicationContext.startForegroundService(serviceIntent)
                } else {
                    notificationOneTimeWorkManager(it)
                }
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
//        notificationOneTimeWorkManager()
//        notificationPeriodWorkManager()
    }

    fun getDistanceRun(steps: Long): String {
        val distance = (steps * 76).toFloat() / 100000f
        val format = DecimalFormat("#.##")
        format.roundingMode = RoundingMode.CEILING
        return format.format(distance).toString()
    }

    override fun onPause() {
        super.onPause()
    }
}