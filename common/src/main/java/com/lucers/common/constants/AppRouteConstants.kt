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

    const val httpRoute: String = "$routeRoot/http"
    const val widgetRoute: String = "$routeRoot/widget"
}