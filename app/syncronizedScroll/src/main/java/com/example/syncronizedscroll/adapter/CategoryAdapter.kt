package com.example.syncronizedscroll.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.syncronizedscroll.Category
import com.example.syncronizedscroll.R

class CategoryAdapter(val context: Context, private val categoryList: List<Category>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryVewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryVewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.item_category_view, parent, false)
        return CategoryVewHolder(view)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryVewHolder, position: Int) {
        holder.categoryText.text = categoryList[position].category
        holder.itemRv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = ItemAdapter(context, categoryList[position].ItemList)
        }
    }


    class CategoryVewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryText: TextView = itemView.findViewById(R.id.category)
        val itemRv: RecyclerView = itemView.findViewById(R.id.itemRV)

    }
}