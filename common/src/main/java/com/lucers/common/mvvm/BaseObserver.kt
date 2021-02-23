package com.lucers.common.mvvm

import com.blankj.utilcode.util.LogUtils
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

/**
 * BaseObserver
 */
abstract class BaseObserver<T> : Observer<T> {

    override fun onSubscribe(d: Disposable?) {
    }

    override fun onNext(response: T) {
    }

    override fun onError(e: Throwable) {
        LogUtils.e(e.message)
    }

    override fun onComplete() {
    }
}