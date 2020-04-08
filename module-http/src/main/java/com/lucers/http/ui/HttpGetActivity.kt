package com.lucers.http.ui

import android.os.Bundle
import com.gyf.immersionbar.ktx.immersionBar
import com.lucers.common.base.BaseActivity
import com.lucers.http.HttpApi
import com.lucers.http.HttpManager
import com.lucers.http.R
import com.lucers.http.bean.HttpResponse
import com.lucers.http.transformer.HttpError
import com.lucers.http.transformer.RxSchedulers
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
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

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
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
            .compose(HttpError.onError())
            .compose(RxSchedulers.schedulers())
            .subscribe(observer)
    }

    private fun simpleRequest() {
        HttpManager.createApi(HttpApi::class.java)
            .getRequest()
            .compose(HttpError.onError())
            .compose(RxSchedulers.schedulers())
            .subscribe(observer)
    }

    private val observer by lazy {
        object : Observer<HttpResponse<String>> {
            override fun onComplete() = hideLoadingWindow()

            override fun onSubscribe(d: Disposable) = showLoadingWindow("${d.hashCode()}")

            override fun onNext(t: HttpResponse<String>) {
                tv_result.text = t.data
            }

            override fun onError(e: Throwable) {
                tv_result.text = e.message
                hideLoadingWindow()
            }
        }
    }
}