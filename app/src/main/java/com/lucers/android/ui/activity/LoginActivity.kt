package com.lucers.android.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.lucers.android.R
import com.lucers.common.base.BaseActivity
import com.lucers.common.constants.AppRouteConstants

@Route(path = AppRouteConstants.loginRoute)
class LoginActivity : BaseActivity(R.layout.activity_login) {

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
    }

    fun toNextActivity() {
        ARouter.getInstance()
            .build(AppRouteConstants.mainRoute)
            .navigation()
        onBackPressed()
    }
}