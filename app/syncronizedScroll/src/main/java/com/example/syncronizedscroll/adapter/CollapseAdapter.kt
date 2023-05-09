package com.example.syncronizedscroll.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.syncronizedscroll.CollapseItem
import com.example.syncronizedscroll.R

class CollapseAdapter(val context: Context, private val collapseItem: CollapseItem) :
    RecyclerView.Adapter<CollapseAdapter.CollapseItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollapseItemViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_collapse, parent, false)
        return CollapseItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return collapseItem.collapseItem.size
    }

    override fun onBindViewHolder(holder: CollapseItemViewHolder, position: Int) {
        holder.collapseTv.text = collapseItem.collapseItem[position]
    }


    class CollapseItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val collapseTv: TextView = itemView.findViewById(R.id.ExtendTv)
    }

}