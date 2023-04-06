package com.example.mystepcounter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mystepcounter.dataClasses.DataList

class RecyclerTabAdapter(val context: Context,  val dataList: DataList) : RecyclerView.Adapter<RecyclerTabAdapter.ViewHolder>() {
    fun updateList(position: Int) {
//        for ((index ,model) in dataList.withIndex()) {
//            model?.isSelected = position == index
//        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_recyclerview_tab, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.titleList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = dataList.titleList[position].title

    }


    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tabTitle)
    }
}