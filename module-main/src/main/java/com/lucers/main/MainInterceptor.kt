package com.lucers.main

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.blankj.utilcode.util.LogUtils
import com.lucers.common.constants.AppRouteConstants

/**
 * MainInterceptor
 */
@Interceptor(priority = 1)
class MainInterceptor : IInterceptor {

    override fun process(postcard: Postcard, callback: InterceptorCallback) {
        if (postcard.path == AppRouteConstants.mainRoute) {
            LogUtils.d("MainInterceptor intercept!")
        }
        callback.onContinue(postcard)
    }

    override fun init(context: Context?) {
        LogUtils.d("MainInterceptor init")
    }
}