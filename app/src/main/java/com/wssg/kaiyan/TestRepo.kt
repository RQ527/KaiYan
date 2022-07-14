package com.wssg.kaiyan

import com.wssg.lib.base.net.RetrofitClient
import com.wssg.lib.base.base.BaseRepository

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/14
 * @Description:
 */
object TestRepo : BaseRepository() {
    fun login(username: String, password: String) =
        executeResp {
            RetrofitClient.getService(ApiService::class.java, isNeedInterCookie = true)
                .login(username, password)
        }

    fun getInfo() = executeResp {
        RetrofitClient.getService(ApiService::class.java, isNeedCookie = true).getInfo()
    }
}