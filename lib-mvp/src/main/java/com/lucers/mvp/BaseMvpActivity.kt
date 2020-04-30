package com.lucers.mvp

import com.lucers.common.base.BaseActivity

/**
 * BaseMvpActivity
 */
class BaseMvpActivity<View : BaseView, Presenter : BasePresenter<View>>(contentLayoutId: Int) : BaseActivity(contentLayoutId) {
}