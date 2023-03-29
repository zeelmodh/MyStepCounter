package com.example.mystepcounter

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.mystepcounter.Fragments.TabOne
import com.example.mystepcounter.Fragments.TabThree
import com.example.mystepcounter.Fragments.TabTwo
import com.example.mystepcounter.databinding.ActivityScrollBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class ScrollAct : AppCompatActivity() {
    lateinit var binding: ActivityScrollBinding

    lateinit var fragOne: TabOne
    lateinit var fragTwo: TabTwo
    lateinit var fragThree: TabThree

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScrollBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tableLayout.isSmoothScrollingEnabled = true
        binding.tableLayout.addTab(binding.tableLayout.newTab().setText("Tab One"))
        binding.tableLayout.addTab(binding.tableLayout.newTab().setText("Tab Two"))
        binding.tableLayout.addTab(binding.tableLayout.newTab().setText("Tab Three"))

        fragOne = TabOne()
        fragTwo = TabTwo()
        fragThree = TabThree()

//        binding.scrollView.viewTreeObserver.addOnScrollChangedListener {
//            val scrollY: Int = binding.scrollView.scrollY
//            var currentTabLayout: TabLayout = tabs
//
//            if (scrollY > (lay_product_vintage.height)) {
//                currentTabLayout.selectedTabPosition == 1
//            }  else if (scrollY > (lay_product_review.height)) {
//                currentTabLayout.selectedTabPosition == 2
//            }
//        }

        binding.tableLayout.setOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.scrollView.isSmoothScrollingEnabled = true
                when(tab.position){
                    0 -> binding.scrollView.scrollTo(0, 0)

                    1 -> binding.scrollView.scrollTo(0, 200)

                    2 -> binding.scrollView.scrollTo(200, 400)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

}