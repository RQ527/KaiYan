package com.wssg.lib.base.base

import com.wssg.lib.base.net.DataState

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/6/23
 * @Description:
 */
open class BaseResp<T>(
    var data: T? = null,
    var errorCode: Int = -1,
    var errorMsg: String? = null,
    var dataState: DataState? = null,
    var error:Throwable?=null
)