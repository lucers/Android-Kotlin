package com.lucers.android.ui.activity

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.lucers.android.R
import com.lucers.android.databinding.ActivitySplashBinding
import com.lucers.common.constants.AppRouteConstants
import com.lucers.mvvm.BaseMvvmActivity
import com.lucers.android.model.SplashModel

/**
 * SplashActivity
 */
@Route(path = AppRouteConstants.splashRoute)
class SplashActivity : BaseMvvmActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val viewModel: SplashModel by lazy {
        ViewModelProvider.AndroidViewModelFactory(application).create(SplashModel::class.java)
    }

    override fun initWindow() {
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        // dataBinding contact viewModel
        dataBinding.viewModel = viewModel
        lifecycle.addObserver(viewModel)
    }

    override fun onBackPressed() {
        //Splash activity can't be back
    }
}
