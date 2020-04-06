package com.lucers.http.bean

/**
 * HttpParam
 */
data class HttpParam(
    var key: String = "",
    var value: String = ""
){
    fun isEmpty(): Boolean {
        return key.isBlank() && value.isBlank()
    }
}