package com.lucers.http

import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.StringUtils
import com.lucers.common.mvvm.BaseObserver
import com.lucers.common.mvvm.BaseViewModel
import com.lucers.http.bean.HttpResponse
import io.reactivex.rxjava3.disposables.Disposable

/**
 * BaseHttpObserver
 */
abstract class BaseHttpObserver<T>(val viewModel: BaseViewModel) : BaseObserver<HttpResponse<T>>() {

    override fun onSubscribe(d: Disposable?) {
        super.onSubscribe(d)
        viewModel.addDisposable(d)
    }

    override fun onNext(response: HttpResponse<T>) {
        super.onNext(response)
        response.data ?: LogUtils.e(StringUtils.getString(R.string.error_net_data_empty))
    }
}