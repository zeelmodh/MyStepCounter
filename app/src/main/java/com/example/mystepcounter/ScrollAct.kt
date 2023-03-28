package com.example.mystepcounter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mystepcounter.databinding.ActivityScrollBinding

class ScrollAct : AppCompatActivity() {
    lateinit var binding: ActivityScrollBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScrollBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tableLayout.isSmoothScrollingEnabled = true
        binding.tableLayout.addTab(binding.tableLayout.newTab().setText("Tab One"))
        binding.tableLayout.addTab(binding.tableLayout.newTab().setText("Tab Two"))
        binding.tableLayout.addTab(binding.tableLayout.newTab().setText("Tab Three"))





    }

}