package com.lucers.http.bean

/**
 * HttpHeader
 */
data class HttpHeader(
    var key: String = "",
    var value: String = ""
) {
    fun isEmpty(): Boolean {
        return key.isBlank() && value.isBlank()
    }
}