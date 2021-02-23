package com.lucers.common.mvvm

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.ActivityUtils
import com.lucers.common.R
import com.lucers.common.base.BaseActivity
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

/**
 * BaseViewModel
 */
open class BaseViewModel : ViewModel(), LifecycleObserver {

    open val titleName: LiveData<Int> = MutableLiveData(R.string.empty)

    val toastMsg: MutableLiveData<String> = MutableLiveData()

    val alertMsg: MutableLiveData<String> = MutableLiveData()

    val showLoading: MutableLiveData<Boolean> = MutableLiveData()

    val showTokenExpiredDialog: MutableLiveData<Boolean> = MutableLiveData()

    val showEditEmailDialog: MutableLiveData<Boolean> = MutableLiveData()

    private val disposables: CompositeDisposable = CompositeDisposable()

    open fun onBackPressed() {
        val activity = ActivityUtils.getTopActivity()
        if (activity is BaseActivity) {
            activity.backActivity()
        } else {
            activity.onBackPressed()
        }
    }

    open fun onStopRequest() {
        disposables.clear()
    }

    override fun onCleared() {
        clearRequest()
        super.onCleared()
    }

    private fun clearRequest() {
        disposables.clear()
    }

    fun addDisposable(disposable: Disposable?) {
        disposables.add(disposable)
    }
}