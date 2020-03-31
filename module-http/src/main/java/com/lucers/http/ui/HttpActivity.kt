package com.lucers.http.ui

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.gyf.immersionbar.ktx.immersionBar
import com.lucers.common.base.BaseActivity
import com.lucers.common.bean.result.AppVersionResult
import com.lucers.http.HttpApi
import com.lucers.http.HttpManager
import com.lucers.http.R
import com.lucers.http.bean.HttpResponse
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_http.*
import java.util.*

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
        rv_http.adapter = HttpFunAdapter()
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
    }
}
