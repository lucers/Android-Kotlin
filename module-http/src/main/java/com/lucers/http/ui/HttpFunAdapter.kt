package com.lucers.http.ui

import com.lucers.common.base.BaseRecyclerViewAdapter
import com.lucers.common.base.BaseViewHolder
import com.lucers.http.R

/**
 * HttpFunAdapter
 */
class HttpFunAdapter : BaseRecyclerViewAdapter<Any>() {

    override fun getItemLayoutId(viewType: Int) = R.layout.item_http_fun

    override fun onBindSimpleViewHolder(holder: BaseViewHolder, item: Any) {

    }
}