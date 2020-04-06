package com.lucers.http.ui.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.lucers.common.base.BaseRecyclerViewAdapter
import com.lucers.common.base.BaseViewHolder
import com.lucers.http.R
import com.lucers.http.bean.HttpHeader

/**
 * HttpHeaderAdapter
 */
class HttpHeaderAdapter : BaseRecyclerViewAdapter<HttpHeader>(
    bottomLayoutId = R.layout.item_add
) {

    override fun getItemLayoutId(viewType: Int) = R.layout.item_key_value

    override fun onBindSimpleViewHolder(holder: BaseViewHolder, item: HttpHeader) {
        holder.setText(R.id.et_key, item.key)
            .setText(R.id.et_value, item.value)
            .setClickListener(R.id.btn_delete, object : View.OnClickListener {
                override fun onClick(v: View?) {
                    list.let {
                        if (it.size == 1) {
                            return
                        }
                        it.remove(item)
                        notifyItemRemoved(holder.adapterPosition)
                    }
                }
            })
            .setTextWatcher(R.id.et_key, object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    s?.let {
                        item.key = s.toString()
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })
            .setTextWatcher(R.id.et_value, object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    s?.let {
                        item.value = s.toString()
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })
    }
}