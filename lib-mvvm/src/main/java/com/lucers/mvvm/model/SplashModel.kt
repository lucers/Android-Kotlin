package com.lucers.mvvm.model

import android.app.Application
import androidx.lifecycle.*
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.LogUtils
import com.lucers.common.constants.AppRouteConstants
import com.lucers.http.transformer.RxSchedulers
import com.lucers.mvvm.BaseAndroidViewModel
import io.reactivex.Observable
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

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun getAdvertisement() {
        // get remote adUrl，onSuccess load into imageView，onFailure skip to next
        LogUtils.d("getAdvertisement")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun startCount() {
        LogUtils.d("startCount")
        _showCount.value = true
        Observable.interval(0L, 1L, TimeUnit.SECONDS)
            .subscribe {
                _countTime.value = totalTime.minus(it)
            }
    }

    fun skip() {
        ARouter.getInstance()
            .build(AppRouteConstants.mainRoute)
            .navigation()
    }
}