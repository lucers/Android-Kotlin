package com.lucers.common.ui.adapter

import com.bumptech.glide.Glide
import com.lucers.common.R
import com.lucers.widget.adapter.BaseRecyclerViewAdapter
import com.lucers.widget.adapter.BaseViewHolder

/**
 * PictureViewAdapter
 */
class PictureViewAdapter : BaseRecyclerViewAdapter<String>() {

    override fun getItemLayoutId(viewType: Int): Int = R.layout.item_picture_view

    override fun onBindSimpleViewHolder(holder: BaseViewHolder, item: String) {
        super.onBindSimpleViewHolder(holder, item)
        Glide.with(holder.context)
            .load(item)
            .into(holder.getView(R.id.iv_picture))
    }
}