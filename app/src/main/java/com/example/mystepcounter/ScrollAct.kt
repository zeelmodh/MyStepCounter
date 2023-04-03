package com.example.mystepcounter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller
import com.example.mystepcounter.Fragments.TabOne
import com.example.mystepcounter.Fragments.TabThree
import com.example.mystepcounter.Fragments.TabTwo
import com.example.mystepcounter.dataClasses.DataList
import com.example.mystepcounter.databinding.ActivityScrollBinding
import com.google.android.material.tabs.TabLayout


class ScrollAct : AppCompatActivity() {
    lateinit var binding: ActivityScrollBinding

    lateinit var listData: DataList
    lateinit var titleDataList: ArrayList<DataList.ListTitle>
    lateinit var descriptionDataList: ArrayList<DataList.ListDescription>
    lateinit var descriptionData: DataList.Description

    private var isScrolling: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScrollBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tableLayout.isSmoothScrollingEnabled = true
        binding.tableLayout.addTab(binding.tableLayout.newTab().setText("Tab One"))
        binding.tableLayout.addTab(binding.tableLayout.newTab().setText("Tab Two"))
        binding.tableLayout.addTab(binding.tableLayout.newTab().setText("Tab Three"))

        /*working but not smooth
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
        })*/

        val titleList = arrayListOf<String>("Tab One", "Tab Two", "Tab Three")
        val desList = arrayListOf<String>(
            "Description for tab One is here \n Description for tab One is here \n Description for tab One is here \n" +
                    " Description for tab One is here \n Description for tab One is here \n" +
                    " Description for tab One is here \n Description for tab One is here \n" +
                    " Description for tab One is here \n Description for tab One is here \n" +
                    " Description for tab One is here \n Description for tab One is here \n" +
                    " Description for tab One is here \n Description for tab One is here \n" +
                    " Description for tab One is here \n Description for tab One is here \n" +
                    " Description for tab One is here \n Description for tab One is here \n" +
                    " Description for tab One is here \n Description for tab One is here \n" +
                    " Description for tab One is here \n Description for tab One is here \n" +
                    " Description for tab One is here \n Description for tab One is here \n" +
                    " Description for tab One is here \n Description for tab One is here \n" +
                    " Description for tab One is here",
            "Description of tab Three is showing here, you can click on tab 2 to get tab three's description up " +
                    "\n Description of tab Three is showing here, you can click on tab 2 to get tab three's description up " +
                    "\n Description of tab Three is showing here, you can click on tab 2 to get tab three's description up " +
                    "\n Description of tab Three is showing here, you can click on tab 2 to get tab three's description up" +
                    "\n Description of tab Three is showing here, you can click on tab 2 to get tab three's description up " +
                    "\n Description of tab Three is showing here, you can click on tab 2 to get tab three's description up " +
                    "\n Description of tab Three is showing here, you can click on tab 2 to get tab three's description up " +
                    "\n Description of tab Three is showing here, you can click on tab 2 to get tab three's description up ",
            "Description of tab Three is showing here, you can click on tab 3 to get tab three's description up " +
                    "\n Description of tab Three is showing here, you can click on tab 3 to get tab three's description up" +
                    "\n Description of tab Three is showing here, you can click on tab 3 to get tab three's description up" +
                    "\n Description of tab Three is showing here, you can click on tab 3 to get tab three's description up" +
                    "\n Description of tab Three is showing here, you can click on tab 3 to get tab three's description up" +
                    "\n Description of tab Three is showing here, you can click on tab 3 to get tab three's description up" +
                    "\n Description of tab Three is showing here, you can click on tab 3 to get tab three's description up" +
                    "\n Description of tab Three is showing here, you can click on tab 3 to get tab three's description up" +
                    "\n Description of tab Three is showing here, you can click on tab 3 to get tab three's description up" +
                    "\n Description of tab Three is showing here, you can click on tab 3 to get tab three's description up" +
                    "\n Description of tab Three is showing here, you can click on tab 3 to get tab three's description up" +
                    "\n Description of tab Three is showing here, you can click on tab 3 to get tab three's description up")


        descriptionDataList = ArrayList()
        descriptionData = DataList.Description(descriptionDataList)
        getTitle(titleList)

        listData = DataList(titleDataList, descriptionData)

        for (i in desList.indices) {
            when (i) {
                0 -> setDescription(desList, i, "Tab One")
                1 -> setDescription(desList, i, "Tab Two")
                2 -> setDescription(desList, i, "Tab Three")
            }
        }

        val linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.apply {
            adapter = RecyclerAdapter(this@ScrollAct, listData)
            layoutManager = linearLayoutManager
        }

        binding.tableLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                isScrolling = false
                binding.recyclerView.smoothScrollToPosition(tab!!.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })


        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (isScrolling) {
                    val position: Int = linearLayoutManager.findFirstVisibleItemPosition()
                    if (position != binding.tableLayout.getSelectedTabPosition()) {
                        val tab: TabLayout.Tab = binding.tableLayout.getTabAt(position)!!
                        tab.select()
                    }
                }
            }
        })
    }

    private fun getTitle(titleList: ArrayList<String>): ArrayList<DataList.ListTitle> {
        titleDataList = ArrayList()
        titleList.forEach { title ->
            titleDataList.add(DataList.ListTitle(title))
        }
        return titleDataList
    }

    private fun setDescription(
        model: ArrayList<String>,
        index: Int,
        title: String
    ): ArrayList<DataList.ListDescription> {
        for (i in model.indices) {
            if (index == i) {
                descriptionDataList.add(DataList.ListDescription(model[i], title, index))
            }
        }
        return descriptionDataList
    }
}