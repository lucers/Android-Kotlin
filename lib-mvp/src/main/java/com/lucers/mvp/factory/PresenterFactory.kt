package com.lucers.mvp.factory

import com.lucers.mvp.BasePresenter
import com.lucers.mvp.BaseView

/**
 * PresenterFactory
 */
interface PresenterFactory<View : BaseView, Presenter : BasePresenter<View>> {

    fun createPresenter(): Presenter
}