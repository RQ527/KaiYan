package com.wssg.lib.base.base

import android.os.Parcelable
import com.wssg.lib.base.net.DataState
import java.io.Serializable

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/6/23
 * @Description:
 */
class BaseResp<T>(
    var itemList: T? = null,
    var count: Int = 0,
    var nextPageUrl: String? = null,
    var dataState: DataState? = null,
    var error: Throwable? = null
)