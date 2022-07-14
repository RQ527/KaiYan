package com.wssg.lib.base.net

import java.lang.Exception

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/6/23
 * @Description:
 */
sealed class ResState<out T:Any>{
    data class Success<out T:Any>(val data:T): ResState<T>()
    data class Error(val exception: Exception): ResState<Nothing>()
}
