package com.example.syncronizedscroll.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.syncronizedscroll.Category
import com.example.syncronizedscroll.R

class CategoryAdapter(val context: Context, private val categoryList: List<Category>, rView: RecyclerView) :
    RecyclerView.Adapter<CategoryAdapter.CategoryVewHolder>() {

    private var expandingPosition:Int = -1
    private val recyclerView :RecyclerView = rView

    private fun expandItem(holder: CategoryVewHolder) {
        downAnimation(holder.itemView)
        holder.itemRv.visibility = View.VISIBLE
    }

    private fun downAnimation(viewToAnimation: View){
        val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.down_side)
        viewToAnimation.startAnimation(animation)
    }

    private fun collapseItem(holder: CategoryVewHolder) {
        upAnimation(holder.itemView)
        holder.itemRv.visibility = View.GONE
    }

    private fun upAnimation(viewToAnimation: View){
        val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.up_side)
        viewToAnimation.startAnimation(animation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryVewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.item_category_view, parent, false)
        return CategoryVewHolder(view)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryVewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.categoryText.text = categoryList[position].category
        holder.itemRv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = ItemAdapter(context, categoryList[position].ItemList)
        }

        holder.itemView.isActivated = true
        holder.itemView.setOnClickListener {
            if (expandingPosition == position) {
                expandingPosition = -1
                collapseItem(holder)

            } else {
                if (expandingPosition != -1) {
                    val lastHolder =
                        recyclerView.findViewHolderForAdapterPosition(expandingPosition) as CategoryVewHolder
                    collapseItem(lastHolder)
                }
                expandingPosition = position
                expandItem(holder)
            }
        }
}


    class CategoryVewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryText: TextView = itemView.findViewById(R.id.category)
        val itemRv: RecyclerView = itemView.findViewById(R.id.itemRV)

    }
}