package com.lucers.android.constants

import com.lucers.android.BuildConfig

/**
 * AppRouteConstants
 */
class AppRouteConstants {

    companion object {
        private const val routeRoot: String = BuildConfig.ROUTE_ROOT

        const val splashRoute: String = "$routeRoot/splash"
        const val mainRoute: String = "$routeRoot/main"
        const val loginRoute: String = "$routeRoot/login"
        const val settingRoute: String = "$routeRoot/setting"
        const val profileRoute: String = "$routeRoot/profile"
        const val browseRecordsRoute: String = "$routeRoot/browse_records"
        const val feedbackRoute: String = "$routeRoot/feedback"
        const val websRoute: String = "$routeRoot/webs"
        const val aboutUsRoute: String = "$routeRoot/about_us"
        const val helpCenterRoute: String = "$routeRoot/help_center"
    }
}