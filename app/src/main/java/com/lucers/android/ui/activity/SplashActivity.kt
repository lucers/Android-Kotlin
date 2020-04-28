package com.lucers.android.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.immersionbar.ktx.immersionBar
import com.lucers.android.R
import com.lucers.common.base.BaseActivity
import com.lucers.common.constants.AppRouteConstants

/**
 * SplashActivity
 */
@Route(path = AppRouteConstants.splashRoute)
class SplashActivity : BaseActivity(R.layout.activity_splash) {

    override fun initWindow() {
        immersionBar {
            transparentNavigationBar()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
    }

    override fun onBackPressed() {
        //Splash activity can't be back
    }
}
