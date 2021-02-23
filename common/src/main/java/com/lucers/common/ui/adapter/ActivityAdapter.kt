package com.lucers.common.ui.adapter

import com.lucers.common.R
import com.lucers.widget.adapter.BaseRecyclerViewAdapter
import com.lucers.widget.adapter.BaseViewHolder

/**
 * ActivityAdapter
 */
class ActivityAdapter : BaseRecyclerViewAdapter<Class<*>>() {

    override fun getItemLayoutId(viewType: Int): Int = R.layout.item_activity

    override fun onBindSimpleViewHolder(holder: BaseViewHolder, item: Class<*>) {
        super.onBindSimpleViewHolder(holder, item)
        holder.setText(R.id.btn_name, item.simpleName)
    }
}