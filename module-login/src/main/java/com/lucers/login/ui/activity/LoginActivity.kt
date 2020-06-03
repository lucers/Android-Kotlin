package com.lucers.login.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.lucers.common.constants.AppRouteConstants
import com.lucers.login.R
import com.lucers.login.databinding.ActivityLoginBinding
import com.lucers.mvvm.BaseMvvmActivity

@Route(path = AppRouteConstants.loginRoute)
class LoginActivity : BaseMvvmActivity<ActivityLoginBinding>(R.layout.activity_login) {

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
    }

    override fun onBackPressed() {
    }
}
