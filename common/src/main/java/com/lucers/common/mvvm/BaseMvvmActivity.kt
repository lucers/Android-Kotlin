package com.lucers.common.mvvm

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ToastUtils
import com.lucers.common.base.BaseActivity
import com.lucers.common.utils.ClassUtils


/**
 * BaseMvvmActivity
 */
abstract class BaseMvvmActivity<DataBinding : ViewDataBinding, VM : BaseViewModel>(
    contentLayoutId: Int,
) : BaseActivity(contentLayoutId) {

    lateinit var dataBinding: DataBinding
    lateinit var viewModel: VM

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        // dataBinding contact UI
        dataBinding = DataBindingUtil.setContentView(this, contentLayoutId)
        dataBinding.lifecycleOwner = this

        ClassUtils.getViewModel<VM>(this)?.let {
            viewModel = ViewModelProvider(this).get(it)
        }

        lifecycle.addObserver(viewModel)

        subscribeUI()
    }

    open fun subscribeUI() {
        viewModel.toastMsg.observe(this, {
            if (it.isNullOrBlank().not()) {
                ToastUtils.showShort(it)
            }
        })
        viewModel.showLoading.observe(this, {
            if (it) {
                startLoading()
            } else {
                stopLoading()
            }
        })
    }

    override fun onBackPressed() {
        viewModel.onStopRequest()
        super.onBackPressed()
    }

    override fun onDestroy() {
        viewModel.onStopRequest()
        super.onDestroy()
    }
}
