package com.lucers.http.ui

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.gyf.immersionbar.ktx.immersionBar
import com.lucers.common.base.BaseActivity
import com.lucers.http.R
import kotlinx.android.synthetic.main.activity_http.*

class HttpActivity : BaseActivity() {

    override fun initWindow() {
        super.initWindow()
        immersionBar {
            statusBarColor(R.color.colorAccent)
        }
    }

    override fun getActivityLayout(): Int = R.layout.activity_http

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        rv_http.layoutManager = LinearLayoutManager(this)
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
    }
}
