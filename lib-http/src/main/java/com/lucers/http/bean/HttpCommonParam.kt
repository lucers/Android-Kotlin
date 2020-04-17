package com.lucers.http.bean

/**
 * HttpParam
 */
data class HttpCommonParam(
    var key: String = "",
    var value: String = ""
) {
    fun isEmpty(): Boolean {
        return key.isBlank() && value.isBlank()
    }
}