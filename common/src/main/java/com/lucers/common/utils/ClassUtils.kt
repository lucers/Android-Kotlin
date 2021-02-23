package com.lucers.common.utils

import androidx.lifecycle.ViewModel
import java.lang.reflect.ParameterizedType


/**
 * ClassUtils
 */
object ClassUtils {

    @Suppress("UNCHECKED_CAST")
    fun <VM : ViewModel> getViewModel(view: Any): Class<VM>? {
        val type = view.javaClass.genericSuperclass
        val parameterizedType: ParameterizedType = type as ParameterizedType
        val actualTypeArguments = parameterizedType.actualTypeArguments
        actualTypeArguments.forEach {
            val clazz: Class<VM> = it as Class<VM>
            if (ViewModel::class.java.isAssignableFrom(it)) {
                return clazz
            }
        }
        return null
    }
}