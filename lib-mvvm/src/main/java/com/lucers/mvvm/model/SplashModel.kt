package com.lucers.mvvm.model

import android.app.Application
import androidx.lifecycle.*
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.LogUtils
import com.lucers.common.constants.AppRouteConstants
import com.lucers.mvvm.BaseAndroidViewModel
import io.reactivex.Observable

/**
 * SplashModel
 */
class SplashModel(application: Application) : BaseAndroidViewModel(application), LifecycleObserver {

    private val _showCount = MutableLiveData(false)
    private val _countTime = MutableLiveData(0)
    private val _posterUrl = MutableLiveData("")

    val showCount: LiveData<Boolean> = _showCount
    val countTime: LiveData<Int> = _countTime
    val posterUrl: LiveData<String> = _posterUrl

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun getPoster() {
        // get remote posterUrl，onSuccess load into imageView，onFailure skip to next
        LogUtils.d("getPoster")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun startCount() {
        LogUtils.d("startCount")
        _showCount.value = true
        _countTime.value = 5
    }

    fun skip() {
        ARouter.getInstance()
            .build(AppRouteConstants.mainRoute)
            .navigation()
    }
}