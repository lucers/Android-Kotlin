package com.lucers.mvvm.model

import android.app.Application
import androidx.lifecycle.*
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.LogUtils
import com.lucers.common.constants.AppRouteConstants
import com.lucers.http.transformer.RxSchedulers
import com.lucers.mvvm.BaseAndroidViewModel
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * SplashModel
 */
class SplashModel(application: Application) : BaseAndroidViewModel(application), LifecycleObserver {

    private val totalTime = 5L

    private val _showCount = MutableLiveData(false)
    private val _countTime = MutableLiveData(0L)
    private val _adUrl = MutableLiveData("")

    val showCount: LiveData<Boolean> = _showCount
    val countTime: LiveData<Long> = _countTime
    val adUrl: LiveData<String> = _adUrl

    var subscribe: Disposable? = null
    var currentTime = 0L

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun getAdvertisement() {
        // get remote adUrl，onSuccess load into imageView，onFailure skip to next
        LogUtils.d("getAdvertisement")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun startCount() {
        LogUtils.d("startCount")
        _showCount.value = true
        subscribe = Observable.interval(currentTime, 1L, TimeUnit.SECONDS)
            .compose(RxSchedulers.schedulers())
            .subscribe {
                currentTime = totalTime.minus(it)
                _countTime.value = currentTime
                if (currentTime == 1L) {
                    skip()
                }
            }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private fun stopCount() {
        subscribe?.dispose()
    }

    fun skip() {
        stopCount()
        ARouter.getInstance()
            .build(AppRouteConstants.mainRoute)
            .navigation()
    }
}