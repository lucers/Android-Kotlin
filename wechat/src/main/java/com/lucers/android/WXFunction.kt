package com.lucers.android

import android.graphics.Bitmap
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.LogUtils
import com.bumptech.glide.util.ByteBufferUtil
import com.lucers.common.base.BaseApplication
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram
import com.tencent.mm.opensdk.modelmsg.*

/**
 * WXFunction
 */
object WXFunction {

    const val userInfoScope: String = "snsapi_userinfo"

    const val sessionScene: Int = SendMessageToWX.Req.WXSceneSession
    const val commentScene: Int = SendMessageToWX.Req.WXSceneTimeline
    const val favoriteScene: Int = SendMessageToWX.Req.WXSceneFavorite

    const val testMiniProgramType: Int = WXMiniProgramObject.MINIPROGRAM_TYPE_TEST
    const val releaseMiniProgramType: Int = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE
    const val previewMiniProgramType: Int = WXMiniProgramObject.MINIPROGRAM_TYPE_PREVIEW

    val wxApi by lazy {
        (BaseApplication.INSTANCE as WXApplication).wxApi
    }

    fun weChatLogin(scope: String, state: String = BaseApplication.INSTANCE.packageName) {
        try {
            if (!wxApi.isWXAppInstalled) {
                return
            }
            val req = SendAuth.Req()
            req.scope = scope
            req.state = state
            wxApi.sendReq(req)
        } catch (e: Exception) {
            LogUtils.e(e.message)
        }
    }

    fun openWeChatMiniProgramer(userName: String, pagePath: String, type: Int) {
        try {
            if (!wxApi.isWXAppInstalled) {
                return
            }
            val req = WXLaunchMiniProgram.Req()
            req.userName = userName
            req.path = pagePath
            req.miniprogramType = type
            wxApi.sendReq(req)
        } catch (e: Exception) {
            LogUtils.e(e.message)
        }
    }

    fun WeChatTextShare(text: String, scene: Int) {
        weChatShare(WXMediaMessage().apply {
            mediaObject = WXTextObject().apply {
                this.text = text
            }
        }, "text", scene)
    }

    fun weChatImageShare(bitmap: Bitmap, scene: Int) {
        weChatShare(WXMediaMessage().apply {
            mediaObject = WXImageObject(bitmap)
            thumbData = ImageUtils.bitmap2Bytes(ImageUtils.scale(bitmap, 100, 100))
        }, "img", scene)
    }

    fun weChatMusicalShare(musicalUrl: String, title: String, description: String, thumbData: ByteArray, scene: Int) {
        weChatShare(WXMediaMessage().apply {
            this.title = title
            this.description = description
            this.thumbData = thumbData
            mediaObject = WXMusicObject().apply {
                this.musicUrl = musicalUrl
            }
        }, "musical", scene)
    }

    fun weChatVideoShare(videoUrl: String, title: String, description: String, thumbData: ByteArray, scene: Int) {
        weChatShare(WXMediaMessage().apply {
            this.title = title
            this.description = description
            this.thumbData = thumbData
            mediaObject = WXVideoObject().apply {
                this.videoUrl = videoUrl
            }
        }, "video", scene)
    }

    fun WeChatUrlShare(url: String, title: String, description: String, thumbData: ByteArray, scene: Int) {
        weChatShare(WXMediaMessage().apply {
            this.title = title
            this.description = description
            this.thumbData = thumbData
            mediaObject = WXWebpageObject().apply {
                webpageUrl = url
            }
        }, "url", scene)
    }

    fun weChatMiniProgramShare(
        url: String,
        type: Int,
        userName: String,
        pagePath: String,
        title: String,
        description: String,
        thumbData: ByteArray,
        scene: Int
    ) {
        weChatShare(WXMediaMessage().apply {
            this.title = title
            this.description = description
            this.thumbData = thumbData
            mediaObject = WXMiniProgramObject().apply {
                webpageUrl = url
                miniprogramType = type
                this.userName = userName
                this.path = pagePath
            }
        }, "url", scene)
    }

    private fun weChatShare(mediaMessage: WXMediaMessage, type: String? = null, scene: Int) {
        try {
            if (!wxApi.isWXAppInstalled) {
                return
            }

            val req = SendMessageToWX.Req().also {
                it.transaction = buildTransaction(type)
                it.message = mediaMessage
                it.scene = scene
                it.userOpenId = getOpenId()
            }
            wxApi.sendReq(req)
        } catch (e: Exception) {
            LogUtils.e(e.message)
        }
    }

    private fun getOpenId(): String {
        return ""
    }

    private fun buildTransaction(type: String?): String {
        return if (type == null) System.currentTimeMillis().toString() else type + System.currentTimeMillis()
    }
}