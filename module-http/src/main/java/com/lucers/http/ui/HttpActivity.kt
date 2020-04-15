package com.lucers.http.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.immersionbar.ktx.immersionBar
import com.lucers.common.base.BaseActivity
import com.lucers.common.base.BaseRecyclerViewAdapter
import com.lucers.common.constants.AppRouteConstants
import com.lucers.http.R
import com.lucers.http.bean.HttpFun
import com.lucers.http.ui.adapter.HttpFunAdapter
import kotlinx.android.synthetic.main.activity_http.*

/**
 * HttpActivity
 */
@Route(path = AppRouteConstants.httpRoute)
class HttpActivity : BaseActivity(R.layout.activity_http) {

    private val httpFunAdapter = HttpFunAdapter()

    private val funList = mutableListOf<HttpFun>()

    override fun initWindow() {
        super.initWindow()
        immersionBar {
            statusBarColor(R.color.colorAccent)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        httpFunAdapter.itemClickListener = funClickListener

        rv_http.adapter = httpFunAdapter
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        funList.add(HttpFun(getString(R.string.http_url), "com.lucers.http.ui.HttpUrlActivity"))
        funList.add(HttpFun(getString(R.string.http_interceptor), "com.lucers.http.ui.HttpInterceptorActivity"))
        funList.add(HttpFun(getString(R.string.http_cache), "com.lucers.http.ui.HttpCacheActivity"))
        funList.add(HttpFun(getString(R.string.http_get), "com.lucers.http.ui.HttpGetActivity"))
        funList.add(HttpFun(getString(R.string.http_post), "com.lucers.http.ui.HttpPostActivity"))

        httpFunAdapter.list = funList
    }

    private val funClickListener = object : BaseRecyclerViewAdapter.OnItemClickListener {
        override fun onItemClick(view: View, position: Int) {
            try {
                val activityClass = Class.forName(funList[position].funActivity)
                startActivity(Intent(applicationContext, activityClass))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
