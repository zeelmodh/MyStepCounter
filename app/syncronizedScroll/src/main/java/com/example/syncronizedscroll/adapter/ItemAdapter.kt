package com.example.syncronizedscroll.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.syncronizedscroll.Item
import com.example.syncronizedscroll.R

class ItemAdapter(val context: Context, private val itemList: Item):
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.item_item_view, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.item.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemText.text = itemList.item[position]

        holder.collapseRView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = CollapseAdapter(context, itemList.collapseItem)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, Class.forName("com.example.mystepcounter.activity.Goto"))
            val itemName = itemList.item[position]
            intent.putExtra("item_name",itemName)
            context.startActivity(intent)
        }
        /*var extendingPosition: Int = -1

        holder.itemView.isActivated = true
        holder.itemView.setOnClickListener {
            if (holder.collapseRView.visibility == View.GONE) {
                extendingPosition = position
                val isExpanded: Boolean = position.toString() == extendingPosition.toString()
                if (isExpanded) holder.collapseRView.visibility = View.VISIBLE

            }else{
                extendingPosition = position
                val isExpanded: Boolean = position.toString() == extendingPosition.toString()

                if (isExpanded) holder.collapseRView.visibility = View.GONE
            }
        }*/
    }


    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val itemText: TextView = itemView.findViewById(R.id.itemTv)
        val collapseRView: RecyclerView = itemView.findViewById(R.id.collapseRView)
    }

}