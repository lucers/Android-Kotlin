package com.lucers.mvvm.model

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.lucers.mvvm.base.BaseViewModel

/**
 * CountDownViewModel
 */
class CountDownViewModel : BaseViewModel(), LifecycleObserver {

    var countTime: Long = 0

    var countOver: Boolean = false

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        countOver = countTime == 0L
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {

    }

    fun finishCount() {
        countOver = true
    }
}