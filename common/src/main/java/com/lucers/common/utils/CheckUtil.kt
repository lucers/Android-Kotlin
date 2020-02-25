package com.lucers.common.utils

import java.util.regex.Pattern

object CheckUtil {

    fun isPhone(phoneText: CharSequence): Boolean {
        val regex = "^((13[0-9])|(14[4-9])|(15[^4])|(16[6-7])|(17[^9])|(18[0-9])|(19[1|8-9]))\\d{8}$"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(phoneText)
        return matcher.matches()
    }
}