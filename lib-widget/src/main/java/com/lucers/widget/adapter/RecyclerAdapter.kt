package com.lucers.widget.adapter

import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.lucers.widget.R

/**
 * RecyclerAdapter
 */
class RecyclerAdapter : BaseRecyclerViewAdapter<String, RecyclerView.ViewHolder>() {

    override fun getItemLayoutId(viewType: Int): Int = R.layout.item_title

    override fun onBindSimpleViewHolder(holder: RecyclerView.ViewHolder, item: String) {
        super.onBindSimpleViewHolder(holder, item)
        holder.itemView.findViewById<AppCompatButton>(R.id.btn_title).text = item
    }
}