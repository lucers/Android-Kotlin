package com.lucers.common.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import com.blankj.utilcode.util.LogUtils
import com.lucers.common.R

/**
 * ActivityAdapter
 */
class ActivityAdapter(val data: MutableList<Class<*>>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(parent?.context).inflate(R.layout.item_activity, parent, false)
        bindView(view, position)
        return view
    }

    private fun bindView(view: View?, position: Int) {
        val btnName = view?.findViewById<Button>(R.id.btn_name)
        btnName?.text = data[position].simpleName
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.size
    }
}