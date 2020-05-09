package com.lucers.widget.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import com.lucers.widget.R

/**
 * Created by Lucers on 2020/5/9.
 */
class GridAdapter(private val data: List<String>) : BaseAdapter() {

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): String {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(parent.context).inflate(R.layout.item_number, parent, false)
        bindView(position, view)
        return view
    }

    private fun bindView(position: Int, view: View) {
        val btnNumber = view.findViewById<Button>(R.id.btn_number)
        btnNumber.text = data[position]
    }
}