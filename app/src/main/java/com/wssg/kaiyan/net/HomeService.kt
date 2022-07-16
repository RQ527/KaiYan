package com.wssg.kaiyan.net

import com.wssg.kaiyan.bean.HomeBean
import com.wssg.lib.base.base.BaseResp
import retrofit2.http.GET

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/14
 * @Description:
 */
interface HomeService {
    @GET("/api/v5/index/tab/allRec")
    suspend fun getHomeData():BaseResp<HomeBean>

}