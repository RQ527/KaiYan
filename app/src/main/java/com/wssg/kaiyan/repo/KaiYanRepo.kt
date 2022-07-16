package com.wssg.kaiyan.repo

import com.wssg.kaiyan.net.HomeService
import com.wssg.kaiyan.net.PlayVideoService
import com.wssg.lib.base.net.RetrofitClient
import com.wssg.lib.base.base.BaseRepository

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/14
 * @Description:
 */
object KaiYanRepo : BaseRepository() {
    fun getHomeData() =
        executeResp { RetrofitClient.getService(HomeService::class.java).getHomeData() }
    fun getVideoInfo(id:String) =
        executeResp { RetrofitClient.getService(PlayVideoService::class.java).getVideoInfo(id) }
}