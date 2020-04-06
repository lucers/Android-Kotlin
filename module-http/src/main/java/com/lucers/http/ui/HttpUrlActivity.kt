package com.lucers.http.ui

import android.annotation.SuppressLint
import android.os.Bundle
import com.blankj.utilcode.util.ToastUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.lucers.common.BuildConfig
import com.lucers.common.base.BaseActivity
import com.lucers.http.HttpManager
import com.lucers.http.R
import kotlinx.android.synthetic.main.activity_http_url.*

/**
 * HttpUrlActivity
 */
class HttpUrlActivity : BaseActivity() {

    override fun initWindow() {
        super.initWindow()
        immersionBar {
            statusBarColor(R.color.colorAccent)
        }
    }

    override fun getActivityLayout(): Int = R.layout.activity_http_url

    @SuppressLint("SetTextI18n")
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        tool_bar.setNavigationOnClickListener { onBackPressed() }
        tv_default_url.text = "默认Url：${BuildConfig.BASE_URL}"

        currentUrl()

        btn_change_url.setOnClickListener {
            changeUrl()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun currentUrl() {
        val currentUrl = HttpManager.getBaseUrl()
        tv_current_url.text = "当前Url: $currentUrl"
    }

    private fun changeUrl() {
        try {
            val httpUrl = et_http_url.text?.toString()?.trim()
            httpUrl?.let {
                HttpManager.updateBaseUrl(it)
            }
        } catch (e: Exception) {
            ToastUtils.showLong(e.message)
        }
        currentUrl()
    }
}