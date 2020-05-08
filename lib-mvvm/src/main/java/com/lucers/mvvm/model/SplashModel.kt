package com.lucers.mvvm.model

import android.app.Application
import androidx.lifecycle.*
import com.blankj.utilcode.util.LogUtils

/**
 * SplashModel
 */
class SplashModel(application: Application) : AndroidViewModel(application), LifecycleObserver {

    var showCount: Boolean = false
    var countTime: Int = 0

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun getPoster() {
        LogUtils.d("getPoster")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun startCount() {
        LogUtils.d("startCount")
        showCount = true
    }
}

class SplashModelFactory(val application: Application) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SplashModel(application) as T
    }
}