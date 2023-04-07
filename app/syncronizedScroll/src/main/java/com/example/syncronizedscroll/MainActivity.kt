package com.example.syncronizedscroll

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller
import com.example.syncronizedscroll.adapter.CategoryAdapter
import com.example.syncronizedscroll.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener {

    lateinit var binding: ActivityMainBinding
    lateinit var categories: List<Category>

    private var indices: List<Int>? = null

    var tabSelected: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addTabs()
        init()

        binding.recyclerView.addOnScrollListener(onScrolling)
    }

    private fun init() {
        val itemOneList = listOf<String>("Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6")
        val itemTwoList = listOf<String>(
            "Item 1",
            "Item 2",
            "Item 3",
            "Item 4",
            "Item 5",
            "Item 6",
            "Item 7",
            "Item 8",
            "Item 9",
            "Item 10"
        )
        val itemThreeList = listOf<String>("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
        val itemFourList = listOf<String>(
            "Item 1",
            "Item 2",
            "Item 3",
            "Item 4",
            "Item 5",
            "Item 6",
            "Item 7",
            "Item 8",
            "Item 9",
            "Item 10"
        )

        categories = mutableListOf(
            Category("Tab One", Item(itemOneList)),
            Category("Tab Two", Item(itemTwoList)),
            Category("Tab Three", Item(itemThreeList)),
            Category("Tab Four", Item(itemFourList))
        )

        binding.recyclerView.apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = CategoryAdapter(this@MainActivity, categories)
        }

        indices = categories.indices.toList()
    }


    private fun addTabs() {
        binding.tableLayout.isTabIndicatorFullWidth = false

        binding.tableLayout.addTab(binding.tableLayout.newTab().setText("Tab One"))
        binding.tableLayout.addTab(binding.tableLayout.newTab().setText("Tab Two"))
        binding.tableLayout.addTab(binding.tableLayout.newTab().setText("Tab Three"))
        binding.tableLayout.addTab(binding.tableLayout.newTab().setText("Tab Four"))

        binding.tableLayout.addOnTabSelectedListener(this@MainActivity)
    }


    override fun onTabSelected(tab: TabLayout.Tab?) {
        tabSelected = true

        val position = tab?.position
        val smoothScroller: SmoothScroller =
            object : LinearSmoothScroller(binding.recyclerView.context) {
                override fun getVerticalSnapPreference(): Int {
                    return SNAP_TO_START
                }
            }

        smoothScroller.targetPosition = indices!![position!!]
        binding.recyclerView.layoutManager?.startSmoothScroll(smoothScroller)
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {}

    override fun onTabReselected(tab: TabLayout.Tab?) {}


    private val onScrolling = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            if (tabSelected) {
                return
            }
            val linearLayoutManager = binding.recyclerView.layoutManager as LinearLayoutManager

            val firstVisiblePosition = linearLayoutManager.findFirstVisibleItemPosition()

            if (binding.recyclerView.scrollState == RecyclerView.SCROLL_STATE_DRAGGING
                || binding.recyclerView.scrollState == RecyclerView.SCROLL_STATE_SETTLING
            ) {

                for (i in indices!!.indices) {
                    if (firstVisiblePosition == indices!![i]) {
                        if (!binding.tableLayout.getTabAt(i)?.isSelected!!) {
                            binding.tableLayout.getTabAt(i)?.select()
                        }
                    }
                }
            }

        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                tabSelected = false
            }
        }

    }
}