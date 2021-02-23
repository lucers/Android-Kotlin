package com.lucers.common.constants

/**
 * AppConstants
 */
object AppConstants {

    const val platformType: Int = 0

    enum class LoginType(val type: Int) {
        FACEBOOK_LOGIN(1),
        GOOGLE_LOGIN(2)
    }

    enum class DeliveryType(val type: Int) {
        NOT_FOUND(1),
        TRANSIT(2),
        PICKUP(3),
        DELIVERED(4),
        UNDELIVERED(5),
        EXCEPTION(6),
        EXPIRED(7)
    }

    const val flashDeal: String = "/category/2062"
    const val hotSelling: String = "/index/best-deal"
    const val newArrival: String = "/index/the-latest-updates"
    const val readyShip: String = "/index/ready-ship-48h"
    const val indexKeyword: String = "/index/keyword/"

    const val dir = "dir"
    const val desc: String = "DESC"
    const val asc: String = "ASC"

    const val pageKey = "P"

    const val sort = "order"
    const val price = "price"
    const val currencyId = "currencyId"
    const val newDate = "newdate"
    const val time = "time"
    const val bestSeller = "basesellers"
    const val soldTotal = "saleTotal"
    const val mostViewed = "mostviewed"
    const val viewCount = "views_count"
    const val topReview = "reviewCount"
    const val reviewCount = "review_count"
    const val orderBy = "orderBy"
    const val productQuantity = "product_quantity"
    const val bestSelling = "best_selling_quantity"

    const val allReview = "all"
    const val pictureReview = "picture"

    const val category: String = "category"
    const val search: String = "search"

    const val reviewPageSize: Int = 10
    const val orderPageSize: Int = 20
    const val flashBuyProductPageSize: Int = 20
    const val keywordPageSize: Int = 30
    const val customerShowPageSize: Int = 30

    val symbolList: List<String> = listOf(
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
        "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    )

    const val requestUpdateProfile: Int = 2001
    const val requestUpdateFirstName: Int = 20011
    const val requestUpdateLastName: Int = 20012

    const val requestChoosePicture: Int = 2002
    const val requestViewPicture: Int = 20021

    const val requestChooseCountry: Int = 2003

    const val requestGoogleLogin: Int = 2004

    const val requestEditAddress: Int = 2005

    const val requestChooseAddress: Int = 2006

    const val requestChooseCoupon: Int = 2007

    const val requestEditReview: Int = 2008

    const val requestWebPay: Int = 2009

    const val requestStripePay: Int = 2010
}