package com.lucers.android.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.lucers.android.R
import com.lucers.common.base.BaseActivity
import com.lucers.common.constants.AppRouteConstants

@Route(path = AppRouteConstants.loginRoute)
class LoginActivity : BaseActivity() {

    override fun getActivityLayout(): Int = R.layout.activity_login
}