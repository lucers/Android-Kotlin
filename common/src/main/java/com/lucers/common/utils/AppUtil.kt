package com.lucers.common.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import com.google.gson.Gson
import com.lucers.common.R
import java.io.BufferedReader
import java.io.InputStreamReader


/**
 * AppUtil
 *
 * @author Lucers
 */
@Suppress("DEPRECATION")
object AppUtil {

    private const val tag = "AppUtil"

    const val weChatPackageName = "com.tencent.mm"
    const val qqPackageName = "com.tencent.mobileqq"

    fun isAppInstalled(context: Context, packageName: String): Boolean {
        return if (TextUtils.isEmpty(packageName)) {
            false
        } else try {
            val applicationInfo: ApplicationInfo =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    context.packageManager
                        .getApplicationInfo(packageName, PackageManager.MATCH_UNINSTALLED_PACKAGES)
                } else {
                    context.packageManager
                        .getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES)
                }
            LogUtil.i(tag, "AppInfo:${Gson().toJson(applicationInfo)}")
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getVersionName(context: Context): String {
        var versionName = ""
        try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            versionName = packageInfo.versionName
        } catch (e: Exception) {
            e.printStackTrace()
        }
        LogUtil.d("versionName:$versionName")
        return versionName
    }

    fun getVersionCode(context: Context): Long {
        var versionCode = 0L
        try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.longVersionCode
            } else {
                packageInfo.versionCode.toLong()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        LogUtil.d("versionCode:$versionCode")
        return versionCode
    }

    fun getJsonFormAssets(context: Context, fileName: String): String {
        var result = ""
        if (TextUtils.isEmpty(fileName)) {
            return result
        }
        val stringBuilder = StringBuilder()
        try {
            val assetManager = context.assets
            val reader = BufferedReader(InputStreamReader(assetManager.open(fileName)))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
            result = stringBuilder.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    fun getAppName(context: Context): String {
        return context.getString(R.string.app_name)
    }

    fun exitToHome(context: Context) {
        val intent = Intent()
        intent.action = Intent.ACTION_MAIN
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun toAppDetail(context: Context) {
        try {
            val pkg = "com.android.settings"
            val cls = "com.android.settings.applications.InstalledAppDetails"
            val intent = Intent()
            intent.component = ComponentName(pkg, cls)
            intent.data = Uri.parse("package:" + context.packageName)
            intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } catch (e: Exception) {
            LogUtil.e(Gson().toJson(e))
        }
    }
}