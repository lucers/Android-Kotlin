package com.lucers.common.constants

import com.lucers.common.BuildConfig

/**
 * AppRouteConstants
 */
object AppRouteConstants {
    private const val routeRoot: String = BuildConfig.ROUTE_ROOT

    const val splashRoute: String = "$routeRoot/splash"
    const val mainRoute: String = "$routeRoot/main"
    const val loginRoute: String = "$routeRoot/login"
    const val settingRoute: String = "$routeRoot/setting"
    const val profileRoute: String = "$routeRoot/profile"
    const val feedbackRoute: String = "$routeRoot/feedback"
    const val websRoute: String = "$routeRoot/webs"
    const val aboutUsRoute: String = "$routeRoot/about_us"
}