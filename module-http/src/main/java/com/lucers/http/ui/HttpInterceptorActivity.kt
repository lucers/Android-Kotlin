package com.lucers.http.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.gyf.immersionbar.ktx.immersionBar
import com.lucers.common.base.BaseActivity
import com.lucers.common.base.BaseRecyclerViewAdapter
import com.lucers.http.HttpManager
import com.lucers.http.R
import com.lucers.http.bean.HttpHeader
import com.lucers.http.bean.HttpParam
import com.lucers.http.ui.adapter.HttpHeaderAdapter
import com.lucers.http.ui.adapter.HttpParamAdapter
import kotlinx.android.synthetic.main.activity_http_interceptor.*

/**
 * HttpInterceptorActivity
 */
class HttpInterceptorActivity : BaseActivity() {

    private val httpHeaderAdapter = HttpHeaderAdapter().also { adapter ->
        adapter.list = HttpManager.getHttpHeader()
        adapter.list.let {
            if (it.isEmpty()) {
                it.add(HttpHeader())
            }
        }
    }

    private val httpParamAdapter = HttpParamAdapter().also { adapter ->
        adapter.list = HttpManager.getCommonParam()
        adapter.list.let {
            if (it.isEmpty()) {
                it.add(HttpParam())
            }
        }
    }

    override fun initWindow() {
        super.initWindow()
        immersionBar {
            statusBarColor(R.color.colorAccent)
        }
    }

    override fun getActivityLayout(): Int = R.layout.activity_http_interceptor

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        tool_bar.setNavigationOnClickListener { onBackPressed() }
        rv_http_header.layoutManager = LinearLayoutManager(this)
        rv_http_header.adapter = httpHeaderAdapter

        rv_common_param.layoutManager = LinearLayoutManager(this)
        rv_common_param.adapter = httpParamAdapter
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        httpHeaderAdapter.bottomClickListener = object : BaseRecyclerViewAdapter.OnBottomClickListener {
            override fun onClick(v: View?) {
                httpHeaderAdapter.list.add(HttpHeader())
                httpHeaderAdapter.list.let { httpHeaderAdapter.notifyItemInserted(it.size) }
            }
        }
        httpParamAdapter.bottomClickListener = object : BaseRecyclerViewAdapter.OnBottomClickListener {
            override fun onClick(v: View?) {
                httpParamAdapter.list.add(HttpParam())
                httpParamAdapter.list.let { httpParamAdapter.notifyItemInserted(it.size) }
            }
        }
    }
}