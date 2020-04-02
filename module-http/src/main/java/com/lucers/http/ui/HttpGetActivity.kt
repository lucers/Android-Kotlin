package com.lucers.http.ui

import android.os.Bundle
import androidx.test.internal.runner.junit3.AndroidSuiteBuilder
import com.blankj.utilcode.util.ToastUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.lucers.common.base.BaseActivity
import com.lucers.http.HttpApi
import com.lucers.http.HttpManager
import com.lucers.http.R
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_http_get.*

/**
 * HttpGetActivity
 */
class HttpGetActivity : BaseActivity() {

    override fun initWindow() {
        super.initWindow()
        immersionBar {
            statusBarColor(R.color.colorAccent)
        }
    }

    override fun getActivityLayout(): Int = R.layout.activity_http_get

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        tool_bar.setNavigationOnClickListener { onBackPressed() }
        btn_simple_request.setOnClickListener {
            simpleRequest()
        }
        btn_url_request.setOnClickListener {
            urlRequest()
        }
    }

    private fun urlRequest() {
        val url = et_http_url.text.toString().trim()
        HttpManager.createApi(HttpApi::class.java)
            .getRequest(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Any> {
                override fun onComplete() {
                    hideLoadingWindow()
                }

                override fun onSubscribe(d: Disposable) {
                    showLoadingWindow(d.toString())
                }

                override fun onNext(t: Any) {
                    tv_result.text = t.toString()
                }

                override fun onError(e: Throwable) {
                    tv_result.text = e.message
                    hideLoadingWindow()
                }
            })
    }

    private fun simpleRequest() {
        HttpManager.createApi(HttpApi::class.java)
            .getRequest()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<String> {
                override fun onComplete() {
                    hideLoadingWindow()
                }

                override fun onSubscribe(d: Disposable) {
                    showLoadingWindow(d.toString())
                }

                override fun onNext(t: String) {
                    tv_result.text = t
                }

                override fun onError(e: Throwable) {
                    tv_result.text = e.message
                    hideLoadingWindow()
                }
            })
    }
}