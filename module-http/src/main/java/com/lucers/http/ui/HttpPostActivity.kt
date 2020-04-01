package com.lucers.http.ui

import android.os.Bundle
import com.gyf.immersionbar.ktx.immersionBar
import com.lucers.common.base.BaseActivity
import com.lucers.http.R
import kotlinx.android.synthetic.main.activity_http_post.*

/**
 * HttpPostActivity
 */
class HttpPostActivity : BaseActivity() {

    override fun initWindow() {
        super.initWindow()
        immersionBar {
            statusBarColor(R.color.colorAccent)
        }
    }

    override fun getActivityLayout(): Int = R.layout.activity_http_post

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        tool_bar.setNavigationOnClickListener { onBackPressed() }
    }
}