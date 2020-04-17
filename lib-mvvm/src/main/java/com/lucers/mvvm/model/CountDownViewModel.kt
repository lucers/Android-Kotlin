package com.lucers.mvvm.model

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import com.blankj.utilcode.util.LogUtils
import com.lucers.mvvm.base.BaseViewModel

/**
 * CountDownViewModel
 */
class CountDownViewModel : BaseViewModel(), LifecycleObserver {

    var countTime: MutableLiveData<Long> = MutableLiveData()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun startCount() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stopCount() {
    }

    fun finishCount() {
        stopCount()
    }
}