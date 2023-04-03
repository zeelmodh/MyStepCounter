package com.example.mystepcounter

import android.icu.text.CaseMap.Title
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mystepcounter.databinding.ScrollRecyclerviewBinding
import com.google.android.material.tabs.TabLayout


class ScrollRecycleView : AppCompatActivity() {
    lateinit var binding: ScrollRecyclerviewBinding
    lateinit var listData: DataList

    lateinit var titleDataList: ArrayList<DataList.ListTitle>
    lateinit var descriptionDataList: ArrayList<DataList.ListDescription>
    lateinit var descriptionData: DataList.Description

    private var selectedSubCatPosition: Int = 0
    private var recyclerTabAdapter: RecyclerTabAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ScrollRecyclerviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        descriptionDataList = ArrayList()

        val titleList = arrayListOf<String>("Tab One", "Tab Two", "Tab Three")
        val desList = arrayListOf<String>(
            "Description for tab One is here", "Description for tab Two, " +
                    "Description of tab Three is showing here, you can click on tab 2 to get tab three's description up," +
                    "Description of tab Three is showing here, you can click on tab 2 to get tab three's description up",
            "Description of tab Three is showing here, you can click on tab 3 to get tab three's description up, " +
                    "Description of tab Three is showing here, you can click on tab 3 to get tab three's description up, " +
                    "Description of tab Three is showing here, you can click on tab 3 to get tab three's description up, " +
                    "Description of tab Three is showing here, you can click on tab 3 to get tab three's description up" +
                    "Description of tab Three is showing here, you can click on tab 3 to get tab three's description up, " +
                    "Description of tab Three is showing here, you can click on tab 3 to get tab three's description up" +
                    "Description of tab Three is showing here, you can click on tab 3 to get tab three's description up, " +
                    "Description of tab Three is showing here, you can click on tab 3 to get tab three's description up" +
                    "Description of tab Three is showing here, you can click on tab 3 to get tab three's description up, " +
                    "Description of tab Three is showing here, you can click on tab 3 to get tab three's description up" +
                    "Description of tab Three is showing here, you can click on tab 3 to get tab three's description up, " +
                    "Description of tab Three is showing here, you can click on tab 3 to get tab three's description up" +
                    "Description of tab Three is showing here, you can click on tab 3 to get tab three's description up, " +
                    "Description of tab Three is showing here, you can click on tab 3 to get tab three's description up" +
                    "Description of tab Three is showing here, you can click on tab 3 to get tab three's description up, " +
                    "Description of tab Three is showing here, you can click on tab 3 to get tab three's description up" +
                    "Description of tab Three is showing here, you can click on tab 3 to get tab three's description up, " +
                    "Description of tab Three is showing here, you can click on tab 3 to get tab three's description up" +
                    "Description of tab Three is showing here, you can click on tab 3 to get tab three's description up, " +
                    "Description of tab Three is showing here, you can click on tab 3 to get tab three's description up"
        )

        getTitle(titleList)

        for (i in desList.indices){
            when(i){
                0 -> setDescription(desList, i, "Tab One")
                1 -> setDescription(desList, i, "Tab Two")
                2 -> setDescription(desList, i, "Tab Three")
            }
        }

        descriptionData = DataList.Description(descriptionDataList)
        listData = DataList(titleDataList, descriptionData)

        binding.recyclerViewTab.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = RecyclerTabAdapter(this@ScrollRecycleView, listData)
            recyclerTabAdapter = RecyclerTabAdapter(this@ScrollRecycleView, listData)
        }


        val linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = RecyclerAdapter(this@ScrollRecycleView, listData)

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    linearLayoutManager.let { lm ->
                        val index = lm.findFirstVisibleItemPosition()
                        if ((listData.desList.descriptionList.size - 1) >= index) {
                            listData.desList.descriptionList[index].let { sd ->
                                if (selectedSubCatPosition != sd.index) {
                                    selectedSubCatPosition = sd.index
//                                    recyclerTabAdapter?.updateList(selectedSubCatPosition)
                                    binding.recyclerView.layoutManager?.scrollToPosition(selectedSubCatPosition)
                                }
                            }
                        }
                    }
                }
            })

        }

        /*binding.tableLayout.isSmoothScrollingEnabled = true
        binding.tableLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                position()
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
        })*/
    }


    private fun getTitle(titleList: ArrayList<String>): ArrayList<DataList.ListTitle> {
        titleDataList = ArrayList()
        titleList.forEach { title ->
            titleDataList.add(DataList.ListTitle(title))
        }
        return titleDataList
    }

    private fun setDescription(model: ArrayList<String>, index: Int, title: String): ArrayList<DataList.ListDescription> {
        for (i in model.indices){
            if (index == i){
                descriptionDataList.add(DataList.ListDescription(model[i], title , index))
            }
        }
        return descriptionDataList
    }

}

data class DataList(var titleList: ArrayList<ListTitle>, var desList: Description) {
    class ListTitle(var title: String)
//    class ListDescription(var description: String)
    class Description(var descriptionList: ArrayList<ListDescription>)
    class ListDescription(var description: String, var title: String, var index: Int)
}










