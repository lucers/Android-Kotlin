package com.lucers.http.ui.adapter

import com.lucers.common.base.BaseRecyclerViewAdapter
import com.lucers.common.base.BaseViewHolder
import com.lucers.http.R
import com.lucers.http.bean.HttpFun

/**
 * HttpFunAdapter
 */
class HttpFunAdapter : BaseRecyclerViewAdapter<HttpFun>() {

    override fun getItemLayoutId(viewType: Int) = R.layout.item_http_fun

    override fun onBindSimpleViewHolder(holder: BaseViewHolder, item: HttpFun) {
        holder.setText(R.id.btn_http_fun, item.funName)
    }
}