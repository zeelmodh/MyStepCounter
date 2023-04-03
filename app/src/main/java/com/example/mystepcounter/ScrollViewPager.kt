package com.example.mystepcounter

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.mystepcounter.Fragments.TabOne
import com.example.mystepcounter.Fragments.TabThree
import com.example.mystepcounter.Fragments.TabTwo
import com.example.mystepcounter.databinding.ScrollViewpagerBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class ScrollViewPager : AppCompatActivity() {
    lateinit var binding: ScrollViewpagerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ScrollViewpagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tableLayout.isSmoothScrollingEnabled = true

        binding.tableLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    /*this will not scroll smooth
                    0 -> binding.scrollView.scrollToDescendant(binding.fragmentOne)*/

                    //these are scrolling smooth.
                    0 -> binding.scrollView.smoothScrollTo(0, binding.fragmentOne.top)

                    1 -> binding.scrollView.smoothScrollTo(0, binding.fragmentOne.bottom)

                    2 -> binding.scrollView.scrollToDescendant(binding.fragmentThree)

                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
        })
    }
}








