package com.example.mystepcounter.ViewModels

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GotoViewModel: ViewModel() {

    val currentItemName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val countDown: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val startYourActivity: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun startCounter(){
        object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val time = millisUntilFinished / 1000
                countDown.value = time.toInt()
            }

            override fun onFinish() {
                startYourActivity.value = true
            }
        }.start()
    }
}