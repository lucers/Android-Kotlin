package com.lucers.android.model

import android.app.Application
import androidx.lifecycle.*
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.LogUtils
import com.lucers.android.ui.activity.SplashActivity
import com.lucers.common.RxSchedulers
import com.lucers.common.constants.AppRouteConstants
import com.lucers.mvvm.BaseAndroidViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * SplashModel
 */
class SplashModel(application: Application) : BaseAndroidViewModel(application), LifecycleObserver {

    private val totalTime = 5L

    private val _showCount = MutableLiveData(false)
    private val _countTime = MutableLiveData(totalTime)
    private val _adUrl = MutableLiveData("")

    val showCount: LiveData<Boolean> = _showCount
    val countTime: LiveData<Long> = _countTime
    val adUrl: LiveData<String> = _adUrl

    var subscribe: Disposable? = null
    var currentTime = 0L

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun getAdvertisement() {
        // get remote adUrl，onSuccess load into imageView，onFailure skip
        LogUtils.d("getAdvertisement")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun startCount() {
        LogUtils.d("startCount")
        _showCount.value = true
        subscribe = Observable.interval(1L, TimeUnit.SECONDS)
            .compose(RxSchedulers.schedulers())
            .subscribe {
                currentTime++
                _countTime.value = totalTime.minus(currentTime)
                if (currentTime == totalTime) {
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
        ActivityUtils.finishActivity(SplashActivity::class.java)
    }
}