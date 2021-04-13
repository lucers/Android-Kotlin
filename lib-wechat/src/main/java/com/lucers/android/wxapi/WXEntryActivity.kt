package com.lucers.android.wxapi

import android.os.Bundle
import com.blankj.utilcode.util.LogUtils
import com.lucers.android.WXFunction
import com.lucers.common.base.BaseActivity
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler

/**
 * WXEntryActivity
 */
class WXEntryActivity : BaseActivity(0), IWXAPIEventHandler {

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        val result: Boolean = WXFunction.wxApi?.handleIntent(intent, this) ?: false
        LogUtils.d("WXEntryActivity handleIntent: $result")
        if (result.not()) {
            finish()
        }
    }

    override fun onResp(baseResp: BaseResp) {
        LogUtils.d("onResp:${baseResp}")
        when (baseResp.type) {
            ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX -> {
                LogUtils.d("Share result: ${baseResp.errCode} --> $baseResp")
            }
            ConstantsAPI.COMMAND_SENDAUTH -> {
                when (baseResp.errCode) {
                    BaseResp.ErrCode.ERR_OK -> {
                        LogUtils.d("Auth result: ${baseResp as SendAuth.Resp}")
                    }
                    BaseResp.ErrCode.ERR_USER_CANCEL -> {

                    }
                    else -> {

                    }
                }
            }
            ConstantsAPI.COMMAND_LAUNCH_WX_MINIPROGRAM -> {
                LogUtils.d("LunchMiniProgram result: ${baseResp as WXLaunchMiniProgram.Resp}")
            }
        }
        finish()
    }

    override fun onReq(baseRep: BaseReq) {
        LogUtils.d("onResp:${baseRep}")
    }
}