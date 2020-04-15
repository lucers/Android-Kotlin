package com.lucers.http.ui

import android.os.Bundle
import com.gyf.immersionbar.ktx.immersionBar
import com.lucers.common.base.BaseActivity
import com.lucers.http.R
import kotlinx.android.synthetic.main.activity_http_cache.*

/**
 * HttpCacheActivity
 */
class HttpCacheActivity : BaseActivity(R.layout.activity_http_cache) {

    override fun initWindow() {
        super.initWindow()
        immersionBar {
            statusBarColor(R.color.colorAccent)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        tool_bar.setNavigationOnClickListener { onBackPressed() }
    }
}