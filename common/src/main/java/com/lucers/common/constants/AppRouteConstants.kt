package com.lucers.common.constants

/**
 * AppRouteConstants
 */
object AppRouteConstants {

    /**
     * appRouteRoot
     */
    private const val appRouteRoot: String = "/app"

    const val mainRoute: String = "$appRouteRoot/main"
    const val splashRoute: String = "$appRouteRoot/splash"
    const val flashBuyRoute: String = "$appRouteRoot/flash_buy"
    const val cartRoute: String = "$appRouteRoot/cart"
    const val webRoute: String = "$appRouteRoot/web"
    const val aboutUsRoute: String = "$appRouteRoot/about_us"
    const val searchRoute: String = "$appRouteRoot/search"
    const val categoryProductListRoute: String = "$appRouteRoot/category_product_list"
    const val productDetailRoute: String = "$appRouteRoot/product_detail"
    const val keywordListRoute: String = "$appRouteRoot/keyword_list"
    const val customerShowRoute: String = "$appRouteRoot/customer_show"
    const val productReviewListRoute: String = "$appRouteRoot/product_review_list"
    const val resetPassword: String = "$appRouteRoot/reset_password"

    /**
     * needLoginRouteRoot
     */
    const val needLoginRouteRoot: String = "/need_login"

    const val accountSettingsRoute: String = "$needLoginRouteRoot/account_settings"
    const val languageSettingRoute: String = "$needLoginRouteRoot/language_setting"
    const val currencySettingRoute: String = "$needLoginRouteRoot/currency_setting"
    const val informationRoute: String = "$needLoginRouteRoot/information"
    const val accountOrdersRoute: String = "$needLoginRouteRoot/account_orders"
    const val contactUsRoute: String = "$needLoginRouteRoot/contact_us"
    const val messageRoute: String = "$needLoginRouteRoot/message"
    const val addressBookRoute: String = "$needLoginRouteRoot/address_book"
    const val editAddressRoute: String = "$needLoginRouteRoot/edit_address"
    const val wishlistRoute: String = "$needLoginRouteRoot/wishlist"
    const val accountReviewsRoute: String = "$needLoginRouteRoot/account_reviews"
    const val couponRoute: String = "$needLoginRouteRoot/coupon"
    const val recentViewed: String = "$needLoginRouteRoot/recent_view"
    const val creditRoute: String = "$needLoginRouteRoot/credit"
    const val editInformation: String = "$needLoginRouteRoot/edit_information"
    const val viewOrderRoute: String = "$needLoginRouteRoot/view_order"
    const val orderDetailRoute: String = "$needLoginRouteRoot/order_detail"
    const val afterSaleRoute: String = "$needLoginRouteRoot/after_sale"
    const val editAfterSaleRoute: String = "$needLoginRouteRoot/edit_after_sale"
    const val helpCenterRoute: String = "$needLoginRouteRoot/help_center"
    const val faqRoute: String = "$needLoginRouteRoot/faq"
    const val logisticsDetailRoute: String = "$needLoginRouteRoot/logistics_detail_route"
    const val chooseCountryRoute: String = "$needLoginRouteRoot/choose_country"
    const val checkOutRoute: String = "$needLoginRouteRoot/check_out"
    const val statusOrderRoute: String = "$needLoginRouteRoot/status_order"
    const val editReviewRoute: String = "$needLoginRouteRoot/edit_review"
    const val payResultRoute: String = "$needLoginRouteRoot/pay_result"
    const val afterSaleChatRoute: String = "$needLoginRouteRoot/after_sale_chat"

    /**
     * loginRouteRoot
     */
    private const val loginRouteRoot: String = "/login"

    const val loginRoute: String = "$loginRouteRoot/login"
    const val forgotPasswordRoute: String = "$loginRouteRoot/forgot_password"

    /**
     * commonRouteRoot
     */
    private const val commonRouteRoot: String = "/common"

    const val activityManageRoute: String = "$commonRouteRoot/activity_manage"
    const val choosePictureRoute: String = "$commonRouteRoot/choose_picture"
    const val pictureViewRoute: String = "$commonRouteRoot/picture_view"
}