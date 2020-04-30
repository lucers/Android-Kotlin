package com.lucers.mvp

import android.os.Bundle
import androidx.lifecycle.LifecycleObserver

/**
 * BasePresenter
 */
class BasePresenter<View : BaseView> : LifecycleObserver {

    private var view: View? = null

    fun onAttachView(view: View) {
        this.view = view
    }

    fun onDetachView() {
        this.view = null
    }

    fun onDestroyPresenter() {

    }
    fun getView(): View? {
        return view
    }
}