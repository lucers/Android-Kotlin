package com.lucers.android

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.lucers.common.base.BaseApplication
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory

/**
 * WXApplication
 */
class WXApplication : BaseApplication() {

    lateinit var wxApi: IWXAPI

    override fun initApplication(context: Context) {
        registerToWeChat()
    }

    private fun registerToWeChat() {
        wxApi = WXAPIFactory.createWXAPI(this, BuildConfig.WX_APP_ID)

        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                wxApi.registerApp(BuildConfig.WX_APP_ID)
            }
        }, IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP))
    }
}