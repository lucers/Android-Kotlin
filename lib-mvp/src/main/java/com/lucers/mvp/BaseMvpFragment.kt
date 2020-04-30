package com.lucers.mvp

import com.lucers.common.base.BaseFragment

/**
 * Created by Lucers on 2020/4/26.
 */
class BaseMvpFragment<View : BaseView, Presenter : BasePresenter<View>>(contentLayoutId: Int) : BaseFragment(contentLayoutId) {

}