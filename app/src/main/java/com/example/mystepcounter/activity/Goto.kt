package com.example.mystepcounter.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.mystepcounter.ViewModels.GotoViewModel
import com.example.mystepcounter.databinding.ActivityGotoBinding

class Goto : AppCompatActivity(){
    private lateinit var binding:ActivityGotoBinding
    private val gotoViewModel : GotoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val getIntent = intent
        gotoViewModel.currentItemName.value = getIntent.getStringExtra("item_name")

        intLiveData()
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    private fun intLiveData() {
        gotoViewModel.startCounter()

        val observerItem = Observer<String> { goToName ->
            binding.tvItemTitle.text = goToName
        }

        val observerCountDown = Observer<Int> { countDown ->
            binding.tvItemTitle.text = countDown.toString()
        }

        val observerStartAct = Observer<Boolean> { startActivity ->
            if (startActivity) {
                binding.tvItemTitle.text = "Start your Activity"
            }
            else {
                binding.tvItemTitle.text = ""
            }
        }


        gotoViewModel.currentItemName.observe(this, observerItem)
        gotoViewModel.countDown.observe(this, observerCountDown)
        gotoViewModel.startYourActivity.observe(this, observerStartAct)
    }
}